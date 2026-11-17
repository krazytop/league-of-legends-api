package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.model.EnumGlobalRegion;
import com.krazytop.leagueoflegends.domain.model.Summoner;
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
     * Récupère les informations principales d'un invocateur
     *
     * @param puuid le puuid de l'invocateur
     * @return les informations de l'invocateur
     */
    public Summoner getSummoner(String puuid) {
        return repositoryPort.findSummoner(puuid)
                .orElseGet(() -> getUpToDateSummoner(puuid));
    }

    /**
     * Récupère les informations principales d'un invocateur
     *
     * @param tag le tag de l'invocateur
     * @param name le nom de l'invocateur
     * @return les informations de l'invocateur
     */
    public Summoner getSummoner(String tag, String name) {
        return repositoryPort.findSummoner(tag, name)
                .orElseGet(() -> getUpToDateSummoner(tag, name));
    }

    /**
     * Récupère les informations principales d'un invocateur à jour
     *
     * @param puuid le puuid de l'invocateur
     * @return les informations de l'invocateur
     */
    private Summoner getUpToDateSummoner(String puuid) {
        return accountRiotPort.fetchAccount(EnumGlobalRegion.EUROPE, puuid)
                .flatMap(account -> summonerRiotPort.fetchSummoner(account.getRegion(), puuid)
                        .map(summoner -> summoner.updateWith(account)))
                .orElseThrow(() -> new ResourceNotFoundException("L'invocateur avec le puuid %s n'existe pas".formatted(puuid)));
    }

    /**
     * Récupère les informations principales d'un invocateur à jour
     *
     * @param tag le tag de l'invocateur
     * @param name le nom de l'invocateur
     * @return les informations de l'invocateur
     */
    private Summoner getUpToDateSummoner(String tag, String name) {
        return accountRiotPort.fetchAccount(EnumGlobalRegion.EUROPE, name, tag)
                .flatMap(account -> summonerRiotPort.fetchSummoner(account.getRegion(), account.getPuuid())
                        .map(summoner -> summoner.updateWith(account)))
                .orElseThrow(() -> new ResourceNotFoundException("L'invocateur %s #%s n'existe pas".formatted(name, tag)));
    }

    /**
     * Met à jour les informations principales d'un invocateur, si c'est la 1ère fois alors il est importé
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

        return repositoryPort.saveSummoner(updatedSummoner);
    }
}
