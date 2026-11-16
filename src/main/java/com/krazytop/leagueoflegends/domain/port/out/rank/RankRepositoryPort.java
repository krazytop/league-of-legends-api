package com.krazytop.leagueoflegends.domain.port.out.rank;

import com.krazytop.leagueoflegends.domain.model.SeasonOrSetRanks;

import java.util.List;

public interface RankRepositoryPort {

    /**
     * Récupère les classements de toutes les saisons ou set et de toutes les queues d'un joueur depuis la BDD
     *
     * @param puuid le puuid du joueur
     * @return la liste des classements du joueur
     */
    List<SeasonOrSetRanks> findRanks(String puuid);

    /**
     * Met à jour les classements de toutes les saisons ou set et de toutes les queues d'un joueur en BDD
     *
     * @param puuid le puuid du joueur
     */
    void saveRanks(List<SeasonOrSetRanks> ranks, String puuid);
}
