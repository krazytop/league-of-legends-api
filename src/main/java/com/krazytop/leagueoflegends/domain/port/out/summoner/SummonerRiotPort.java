package com.krazytop.leagueoflegends.domain.port.out.summoner;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Summoner;

import java.util.Optional;

public interface SummonerRiotPort {

    /**
     * Récupère les informations de l'invocateur depuis l'api de Riot.
     *
     * @param region la région de l'invocateur
     * @param puuid le puuid de l'invocateur
     * @return les informations de l'invocateur
     */
    Optional<Summoner> fetchSummoner(EnumRegion region, String puuid);
}
