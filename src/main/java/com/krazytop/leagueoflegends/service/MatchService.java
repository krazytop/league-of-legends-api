package com.krazytop.leagueoflegends.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.MatchMapper;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.nomenclature.QueueEnum;
import com.krazytop.leagueoflegends.nomenclature.RoleEnum;
import com.krazytop.leagueoflegends.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {

    @Value("${spring.data.web.pageable.default-page-size:5}")
    private int pageSize;
    @Value("${league-of-legends.api-key:api-key}")
    private String apiKey;
    private final MatchRepository matchRepository;
    private final SummonerService summonerService;
    private final MatchMapper matchMapper;
    private final ArenaCompletionService arenaCompletionService;

    public List<MatchDTO> getMatches(String puuid, Integer pageNb, String queue, String role) {
        return getMatches(puuid, pageNb, QueueEnum.fromName(queue), RoleEnum.fromName(role)).stream().map(matchMapper::toDTO).toList();
    }

    public Integer getMatchesCount(String puuid, String queue, String role) {
        return getMatchesCount(puuid, QueueEnum.fromName(queue), RoleEnum.fromName(role));
    }

    public void updateMatches(String puuid) {
        try {
            boolean moreMatchToRecovered = true;
            int firstIndex = 0;
            while (moreMatchToRecovered) {// TODO faire un appel pour chaque region (europe, americas, asia, sea)
                String url = String.format("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?start=%d&count=%d&api_key=%s", puuid, firstIndex, 100, apiKey);
                ObjectMapper mapper = new ObjectMapper();
                List<String> matchIds = mapper.convertValue(mapper.readTree(new URI(url).toURL()), new TypeReference<>() {});
                if (matchIds.isEmpty()) moreMatchToRecovered = false;
                matchIds.add("EUW1_7458650511");
                for (String matchId : matchIds) {
                    Optional<Match> existingMatch = this.matchRepository.findFirstById(matchId);
                    if (existingMatch.isEmpty()) {
                        String stringUrl = String.format("https://europe.api.riotgames.com/lol/match/v5/matches/%s?api_key=%s", matchId, apiKey);
                        JsonNode node = mapper.readTree(new URI(stringUrl).toURL());
                        Match match = mapper.convertValue(node.get("info"), Match.class);
                        if (!match.getParticipants().isEmpty()) {
                            match.setId(node.get("metadata").get("matchId").asText());
                            if (QueueEnum.ARENA.getIds().contains(match.getQueue())) {
                                match.dispatchParticipantsInTeamsArena();
                                arenaCompletionService.updateArenaCompletion(puuid, match);
                            } else {
                                match.dispatchParticipantsInTeamsNormalGame();
                                match.setRemake(match.getTeams().getFirst().getParticipants().getFirst().getGameEndedInEarlySurrender());
                            }
                            saveMatch(match, puuid);
                        } else {
                            log.warn("Match {} is broken", matchId);
                        }
                        Thread.sleep(2000);
                    } else if (!existingMatch.get().getOwners().contains(puuid)) {
                        if (QueueEnum.ARENA.getIds().contains(existingMatch.get().getQueue())) {
                            existingMatch.get().dispatchParticipantsInTeamsArena();
                            arenaCompletionService.updateArenaCompletion(puuid, existingMatch.get());
                        }
                        saveMatch(existingMatch.get(), puuid);
                    } else {
                        moreMatchToRecovered = false;
                        break;
                    }
                }
                Thread.sleep(2000);
                firstIndex += 100;
            }
        } catch (IOException | URISyntaxException | InterruptedException ex) {
            throw new CustomException(ApiErrorEnum.MATCH_UPDATE_ERROR, ex);
        }
    }

    private void saveMatch(Match match, String puuid) {
        match.getOwners().add(puuid);
        log.info("Saving LOL match : {}", match.getId());
        matchRepository.save(match);
        summonerService.updateSpentTimeAndPlayedSeasonsOrSets(puuid, match.getDuration(), Integer.valueOf(match.getVersion().replaceAll("\\..*", "")));
    }

    private List<Match> getMatches(String puuid, int pageNb, QueueEnum queue, RoleEnum role) {
        PageRequest pageRequest = PageRequest.of(pageNb, pageSize);
        if (queue == QueueEnum.ALL_QUEUES) {
            if (role == RoleEnum.ALL_ROLES) {
                return this.matchRepository.findAll(puuid, pageRequest).getContent();
            } else {
                return this.matchRepository.findAllByRole(puuid, role.getRiotName(), pageRequest).getContent();
            }
        } else {
            if (role == RoleEnum.ALL_ROLES) {
                return this.matchRepository.findAllByQueue(puuid, queue.getIds(), pageRequest).getContent();
            } else {
                return this.matchRepository.findAllByQueueAndByRole(puuid, queue.getIds(), role.getRiotName(), pageRequest).getContent();
            }
        }
    }

    private Integer getMatchesCount(String puuid, QueueEnum queue, RoleEnum role) {
        if (queue == QueueEnum.ALL_QUEUES) {
            if (role == RoleEnum.ALL_ROLES) {
                return this.matchRepository.countAll(puuid);
            } else {
                return this.matchRepository.countAllByRole(puuid, role.getRiotName());
            }
        } else {
            if (role == RoleEnum.ALL_ROLES) {
                return this.matchRepository.countAllByQueue(puuid, queue.getIds());
            } else {
                return this.matchRepository.countAllByQueueAndByRole(puuid, queue.getIds(), role.getRiotName());
            }
        }
    }

}
