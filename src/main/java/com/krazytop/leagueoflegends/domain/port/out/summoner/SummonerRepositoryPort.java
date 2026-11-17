package com.krazytop.leagueoflegends.domain.port.out.summoner;

import com.krazytop.leagueoflegends.domain.model.Summoner;

import java.util.Optional;

public interface SummonerRepositoryPort {

    /**
     * Récupère un invocateur depuis la BDD
     *
     * @param puuid le puuid de l'invocateur
     * @return l'invocateur
     */
    Optional<Summoner> findSummoner(String puuid);

    /**
     * Récupère un invocateur depuis la BDD
     *
     * @param tag le tag de l'invocateur
     * @param name le nom de l'invocateur
     * @return l'invocateur
     */
    Optional<Summoner> findSummoner(String tag, String name);

    /**
     * Met à jour l'invocateur en BDD
     *
     * @param summoner l'invocateur
     * @return l'invocateur mis à jour
     */
    Summoner saveSummoner(Summoner summoner);
}
