package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.SeasonRanks;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.port.out.rank.RankRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.rank.RankRiotPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RankService {

    private final RankRiotPort riotPort;
    private final RankRepositoryPort repositoryPort;
    private final MetadataService metadataService;
    private final SummonerService summonerService;
    private final Clock clock;

    /**
     * Récupère les classements de toutes les saisons et de toutes les queues d'un invocateur.
     *
     * @param puuid le puuid de l'invocateur
     * @return la liste des maîtrises de l'invocateur
     */
    public List<SeasonRanks> getRanks(String puuid) {
        return repositoryPort.findRanks(puuid);
    }

    /**
     * Met à jour les classements d'un invocateur.
     *
     * @param puuid le puuid de l'invocateur
     */
    public void updateRanks(String puuid) {
        EnumRegion region = summonerService.getSummonerRegion(puuid);
        int currentSeason = metadataService.getCurrentSeason();
        List<QueueRanks> fetchedQueues = riotPort.fetchRanks(region, puuid);
        List<SeasonRanks> allRanks = new ArrayList<>(getRanks(puuid));

        SeasonRanks currentSeasonRanks = allRanks.stream()
                .filter(season -> Objects.equals(season.getSeason(), currentSeason))
                .findFirst()
                .orElseGet(() -> {
                    SeasonRanks newSeason = new SeasonRanks()
                            .setSeason(currentSeason)
                            .setQueueRanks(new ArrayList<>());
                    allRanks.add(newSeason);
                    return newSeason;
                });

        List<QueueRanks> existingQueues = new ArrayList<>(currentSeasonRanks.getQueueRanks());

        fetchedQueues.stream()
                        .flatMap(fetchedQueue -> fetchedQueue.getRankInformations().stream())
                                .forEach(rank -> rank.setDate(Instant.now(clock)));

        fetchedQueues.forEach(fetchedQueue -> existingQueues.stream()
                .filter(queue -> Objects.equals(queue.getName(), fetchedQueue.getName()))
                .findFirst()
                .ifPresentOrElse(
                        existingQueue -> existingQueue.mergeRankInformations(fetchedQueue.getRankInformations()),
                        () -> existingQueues.add(fetchedQueue)
                ));

        currentSeasonRanks.setQueueRanks(existingQueues);
        repositoryPort.saveRanks(allRanks, puuid);
    }
}
