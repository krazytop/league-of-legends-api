package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.port.out.account.AccountRiotPort;
import com.krazytop.leagueoflegends.domain.port.out.summoner.SummonerRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.summoner.SummonerRiotPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@AllArgsConstructor
public class SummonerService {

    private final AccountRiotPort accountRiotPort;
    private final SummonerRiotPort summonerRiotPort;
    private final SummonerRepositoryPort repositoryPort;
    private final Clock clock;

    /**
     * Récupère les informations principales d'un invocateur.
     *
     * @param puuid le puuid de l'invocateur
     * @return les informations de l'invocateur
     */
    public Summoner getOrFetchSummoner(String puuid) {
        return repositoryPort.findSummoner(puuid)
                .orElseGet(() -> getUpToDateSummoner(puuid));
    }

    /**
     * Récupère les informations principales d'un invocateur.
     *
     * @param tag le tag de l'invocateur
     * @param name le nom de l'invocateur
     * @return les informations de l'invocateur
     */
    public Summoner getOrFetchSummoner(String tag, String name) {
        return repositoryPort.findSummoner(tag, name)
                .orElseGet(() -> getUpToDateSummoner(tag, name));
    }

    /**
     * Récupère les informations principales d'un invocateur à jour.
     *
     * @param puuid le puuid de l'invocateur
     * @return les informations de l'invocateur
     */
    private Summoner getUpToDateSummoner(String puuid) {
        return accountRiotPort.fetchAccount(puuid)
                .flatMap(account -> summonerRiotPort.fetchSummoner(account.getRegion(), puuid)
                        .map(summoner -> summoner.updateWith(account)))
                .orElseThrow(() -> new ResourceNotFoundException("L'invocateur avec le puuid %s n'existe pas".formatted(puuid)));
    }

    /**
     * Récupère les informations principales d'un invocateur à jour.
     *
     * @param tag le tag de l'invocateur
     * @param name le nom de l'invocateur
     * @return les informations de l'invocateur
     */
    private Summoner getUpToDateSummoner(String tag, String name) {
        return accountRiotPort.fetchAccount(name, tag)
                .flatMap(account -> summonerRiotPort.fetchSummoner(account.getRegion(), account.getPuuid())
                        .map(summoner -> summoner.updateWith(account)))
                .orElseThrow(() -> new ResourceNotFoundException("L'invocateur %s #%s n'existe pas".formatted(name, tag)));
    }

    /**
     * Récupère l'invocateur avant de faire un traitement.
     * Permet, au passage, de s'assurer que l'invocateur est déjà importé.
     *
     * @param puuid le puuid de l'invocateur
     * @return les informations de l'invocateur
     * @throws ResourceNotFoundException si l'invocateur n'est pas encore importé
     */
    public Summoner getSummoner(String puuid) {
        return repositoryPort.findSummoner(puuid)
                .orElseThrow(() -> new ResourceNotFoundException("L'invocateur doit être importé avant de faire cette action"));
    }

    /**
     * Récupère la region de l'invocateur
     *
     * @param puuid le puuid de l'invocateur
     * @return la region de l'invocateur
     */
    public EnumRegion getSummonerRegion(String puuid) {
        return getSummoner(puuid).getRegion();
    }

    /**
     * Met à jour les informations principales d'un invocateur, si c'est la 1ère fois alors il est importé.
     * 
     * @param puuid le puuid de l'invocateur
     * @return l'invocateur mis à jour
     */
    public Summoner updateSummoner(String puuid) {
        Summoner updatedSummoner = getUpToDateSummoner(puuid)
                .setUpdateDate(Instant.now(clock));

        repositoryPort.findSummoner(puuid).ifPresent(existing -> updatedSummoner
                .setSpentTime(existing.getSpentTime())
                .setPlayedSeasons(existing.getPlayedSeasons()));

        return saveSummoner(updatedSummoner);
    }

    /**
     * Met à jour les informations principales d'un invocateur,
     *
     * @param summoner les informations à jour du summoner
     * @return l'invocateur mis à jour
     */
    public Summoner saveSummoner(Summoner summoner) {
        return repositoryPort.saveSummoner(summoner);
    }
}
