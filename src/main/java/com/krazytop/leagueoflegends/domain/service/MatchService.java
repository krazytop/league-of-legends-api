package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.InvalidSearchCriteriaException;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.FailedMatch;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;
import com.krazytop.leagueoflegends.domain.port.out.match.FailedMatchRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.match.MatchRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.match.MatchRiotPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class MatchService {

    private static final int BATCH_SIZE = 100;

    private final MatchRiotPort riotPort;
    private final MatchRepositoryPort matchRepositoryPort;
    private final FailedMatchRepositoryPort failedMatchRepositoryPort;
    private final SummonerService summonerService;
    private final ArenaCompletionService arenaCompletionService;
    private final Clock clock;

    /**
     * Récupère les matchs suivant plusieurs critères.
     *
     * @param pageRequest les critères de recherche et de pagination
     * @return la liste des classements de l'invocateur
     */
    public PageResult<Match> searchMatches(PageRequest<MatchSearchCriteria> pageRequest) {
        MatchSearchCriteria criteria = pageRequest.getSearchCriteria();
        if (criteria != null && criteria.getRole() != null && StringUtils.isBlank(criteria.getPuuid())) {
            throw new InvalidSearchCriteriaException("Le filtre 'role' nécessite de renseigner le 'puuid' de l'invocateur");
        }
        return matchRepositoryPort.findMatches(pageRequest);
    }

    /**
     * Met à jour les matchs d'un invocateur.
     *
     * @param puuid le puuid de l'invocateur
     * @param forceFullSync force la synchronisation totale de l'ensemble des matches
     */
    public void updateMatches(String puuid, boolean forceFullSync) {
        Summoner summoner = summonerService.getSummoner(puuid);
        Set<String> completedArenaChampions = arenaCompletionService.getArenaCompletion(puuid);

        getNewMatchIds(summoner, forceFullSync)
                .forEach(matchId -> updateMatch(summoner, completedArenaChampions, matchId));

        summonerService.saveSummoner(summoner);
        arenaCompletionService.saveArenaCompletion(completedArenaChampions, puuid);
    }

    /**
     * Récupère tous les ids des matchs à traiter pour un invocateur.
     * Soit le match n'est pas encore importé, soit un autre invocateur l'a importé mais l'invocateur d'entrée n'est pas affecté dans owners
     *
     * @param summoner l'invocateur
     * @param forceFullSync force la synchronisation totale de l'ensemble des matches
     * @return la liste des ids des matchs à traiter
     */
    private List<String> getNewMatchIds(Summoner summoner, boolean forceFullSync) {
        String puuid = summoner.getPuuid();
        List<String> newMatchIds = new ArrayList<>();
        int start = 0;
        boolean hasMoreMatchToFetch = true;

        while (hasMoreMatchToFetch) {
            List<String> batchIds = riotPort.getMatchIds(summoner.getRegion(), puuid, start, BATCH_SIZE);
            if (batchIds.isEmpty()) {
                break;
            }

            Set<String> existingMatches = matchRepositoryPort.findExistingMatchIdsByOwner(batchIds, puuid);
            Set<String> existingFailedMatches = failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(batchIds, puuid);

            for (String matchId : batchIds) {
                // Si le match existe déjà en base et est déjà associé à l'invocateur alors c'est que l'on est à jour
                if (existingMatches.contains(matchId) || existingFailedMatches.contains(matchId)) {
                    if (forceFullSync) {
                        continue;
                    }
                    return newMatchIds;
                }

                newMatchIds.add(matchId);
            }

            hasMoreMatchToFetch = batchIds.size() == BATCH_SIZE;
            start += BATCH_SIZE;
        }

        log.info("{} nouveau(x) match(s) trouvé(s) pour le puuid {}", newMatchIds.size(), puuid);
        return newMatchIds;
    }

    /**
     * Met à jour un match donné pour un invocateur.
     * Si le match est déjà enregistré alors on met à jour la liste des puuid, sinon on récupère le match depuis l'api de Riot.
     * On met également à jour des données annexes comme les informations du summoner et l'achèvement d'Arena.
     *
     * @param summoner l'invocateur
     * @param completedArenaChampions les champions avec lesquels un invocateur a gagné en Arena
     * @param matchId l'id du match à traiter
     */
    private void updateMatch(Summoner summoner, Set<String> completedArenaChampions, String matchId) {
        String puuid = summoner.getPuuid();

        // 1. Si le match est déjà importé : ajoute l'owner et met à jour le summoner (saisons jouées, durée de jeu) & l'achèvement d'Arena
        Optional<Match> existingMatch = matchRepositoryPort.addOwnerIfMatchExists(matchId, puuid);
        if (existingMatch.isPresent()) {
            updateSummonerAndArenaCompletion(summoner, completedArenaChampions, existingMatch.get());
            return;
        }

        // 2. Si le match est déjà en échec : ajoute juste l'owner
        if (failedMatchRepositoryPort.addOwnerIfFailedMatchExists(matchId, puuid)) return;

        // 3. Sinon : on importe le match et met à jour le summoner (saisons jouées, durée de jeu) & l'achèvement d'Arena
        try {
            Match match = riotPort.getMatch(summoner.getRegion(), matchId)
                    .setOwners(Set.of(puuid));
            matchRepositoryPort.saveMatch(match);

            updateSummonerAndArenaCompletion(summoner, completedArenaChampions, match);
        } catch (Exception e) {
            log.error("Impossible de récupérer ou enregistrer le match {} : {}", matchId, e.getMessage(), e);
            Instant now = Instant.now(clock);
            FailedMatch failedMatch = new FailedMatch()
                    .setId(matchId)
                    .setRegion(EnumRegion.EUW1)
                    .setOwners(Set.of(puuid))
                    .setErrorType(e.getClass().getSimpleName())
                    .setErrorMessage(e.getMessage())
                    .setCreatedAt(now)
                    .setLastAttemptAt(now);
            failedMatchRepositoryPort.saveFailedMatch(failedMatch);
        }
    }

    /**
     * Met à jour les informations du summoner et l'achèvement d'Arena après un match.
     *
     * @param summoner l'invocateur
     * @param completedArenaChampions les champions avec lesquels un invocateur a gagné en Arena
     * @param match le match venant d'être importé pour l'invocateur
     */
    private void updateSummonerAndArenaCompletion(Summoner summoner, Set<String> completedArenaChampions, Match match) {
        summoner.updateWith(match);
        match.getArenaWonChampionFor(summoner.getPuuid())
                .ifPresent(completedArenaChampions::add);
    }
}