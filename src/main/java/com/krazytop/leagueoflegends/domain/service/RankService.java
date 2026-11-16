package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.RankInformations;
import com.krazytop.leagueoflegends.domain.model.SeasonOrSetRanks;
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
    private final Clock clock;

    /**
     * Récupère les classements de toutes les saisons ou set et de toutes les queues d'un joueur
     *
     * @param puuid le puuid du joueur
     * @return la liste des maîtrises du joueur
     */
    public List<SeasonOrSetRanks> getRanks(String puuid) {
        return repositoryPort.findRanks(puuid);
    }

    /**
     * Met à jour les classements d'un joueur.
     *
     * @param puuid le puuid du joueur
     */
    public void updateRanks(String puuid) {
        int currentSeasonOrSet = metadataService.getCurrentSeason();
        List<QueueRanks> fetchedQueues = riotPort.fetchRanks(EnumRegion.EUW1, puuid);//TODO region : à voir soit findSummoner en bdd soit api riot soit erreur
        List<SeasonOrSetRanks> allRanks = new ArrayList<>(getRanks(puuid));

        SeasonOrSetRanks currentSeasonRanks = allRanks.stream()
                .filter(season -> Objects.equals(season.getSeasonOrSetNumber(), currentSeasonOrSet))
                .findFirst()
                .orElseGet(() -> {
                    SeasonOrSetRanks newSeason = new SeasonOrSetRanks()
                            .setSeasonOrSetNumber(currentSeasonOrSet)
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
                        existingQueue -> mergeRankInformations(existingQueue, fetchedQueue.getRankInformations()),
                        () -> existingQueues.add(fetchedQueue)
                ));

        currentSeasonRanks.setQueueRanks(existingQueues);
        repositoryPort.saveRanks(allRanks, puuid);
    }

    private void mergeRankInformations(QueueRanks existingQueue, List<RankInformations> newInfos) {
        List<RankInformations> updatedInfos = new ArrayList<>(existingQueue.getRankInformations());

        newInfos.stream()
                .filter(newInfo -> updatedInfos.isEmpty()
                        || !hasSameGamesPlayed(updatedInfos.getLast(), newInfo))
                .forEach(updatedInfos::add);

        existingQueue.setRankInformations(updatedInfos);
    }

    private boolean hasSameGamesPlayed(RankInformations previous, RankInformations current) {
        return (previous.getWins() + previous.getLosses()) == (current.getWins() + current.getLosses());
    }
}
