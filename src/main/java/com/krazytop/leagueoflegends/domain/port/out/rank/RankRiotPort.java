package com.krazytop.leagueoflegends.domain.port.out.rank;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;

import java.util.List;

public interface RankRiotPort {

    /**
     * Récupère les classements, de toutes les queues, de la saison ou set actuel, d'un joueur depuis l'api de RIOT
     *
     * @param region la région du joueur
     * @param puuid le puuid du joueur
     * @return la liste des classements du joueur
     */
    List<QueueRanks> fetchRanks(EnumRegion region, String puuid);
}
