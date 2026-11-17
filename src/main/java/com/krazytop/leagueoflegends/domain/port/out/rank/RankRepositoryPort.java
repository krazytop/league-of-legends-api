package com.krazytop.leagueoflegends.domain.port.out.rank;

import com.krazytop.leagueoflegends.domain.model.SeasonOrSetRanks;

import java.util.List;

public interface RankRepositoryPort {

    /**
     * Récupère les classements de toutes les saisons ou set et de toutes les queues d'un invocateur depuis la BDD
     *
     * @param puuid le puuid de l'invocateur
     * @return la liste des classements de l'invocateur
     */
    List<SeasonOrSetRanks> findRanks(String puuid);

    /**
     * Met à jour les classements de toutes les saisons ou set et de toutes les queues d'un invocateur en BDD
     *
     * @param puuid le puuid de l'invocateur
     */
    void saveRanks(List<SeasonOrSetRanks> ranks, String puuid);
}
