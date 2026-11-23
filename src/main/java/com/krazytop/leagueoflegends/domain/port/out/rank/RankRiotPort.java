package com.krazytop.leagueoflegends.domain.port.out.rank;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;

import java.util.List;

public interface RankRiotPort {

    /**
     * Récupère les classements, de toutes les queues, de la saison actuelle, d'un invocateur depuis l'api de Riot.
     *
     * @param region la région de l'invocateur
     * @param puuid le puuid de l'invocateur
     * @return la liste des classements de l'invocateur
     */
    List<QueueRanks> fetchRanks(EnumRegion region, String puuid);
}