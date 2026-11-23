package com.krazytop.leagueoflegends.domain.port.out.account;

import com.krazytop.leagueoflegends.domain.model.Account;

import java.util.Optional;

public interface AccountRiotPort {

    /**
     * Récupère le compte d'un invocateur depuis l'api de Riot.
     *
     * @param puuid le puuid de l'invocateur
     * @return le compte de l'invocateur
     */
    Optional<Account> fetchAccount(String puuid);

    /**
     * Récupère le compte d'un invocateur depuis l'api de Riot.
     *
     * @param name le nom de l'invocateur
     * @param tag le tag de l'invocateur
     * @return le compte de l'invocateur
     */
    Optional<Account> fetchAccount(String name, String tag);
}
