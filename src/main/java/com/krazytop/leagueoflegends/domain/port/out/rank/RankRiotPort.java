package com.krazytop.leagueoflegends.domain.port.out.rank;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;

import java.util.List;

public interface RankRiotPort {

    /**
     * Récupère les classements, de toutes les queues, de la saison ou set actuel, d'un invocateur depuis l'api de RIOT
     *
     * @param region la région de l'invocateur
     * @param puuid le puuid de l'invocateur
     * @return la liste des classements de l'invocateur
     */
    List<QueueRanks> fetchRanks(EnumRegion region, String puuid);
}
