package com.krazytop.leagueoflegends.cucumber.steps;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.fr.Etantdonnéque;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.ShardableGame;
import no.stelar7.api.r4j.basic.utils.Utils;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import no.stelar7.api.r4j.pojo.shared.RiotAccountRegion;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RiotApiStep extends LeagueOfLegendsIT {//TODO region au lieu de any

    private final Gson gson = Utils.getGson();

    @Etantdonnéque("l'API Riot ne renvoie aucun compte pour le puuid {string}")
    public void mockNoRiotPuuidAccount(String puuid) {
        when(r4j.getAccountAPI()
                .getAccountByPUUID(any(), eq(puuid)))
                .thenReturn(null);
    }

    @Etantdonnéque("l'API Riot renvoie le compte suivant pour le puuid {string} :")
    public void mockRiotPuuidAccount(String puuid, String jsonPayload) {
        RiotAccount r4jAccount = gson.fromJson(jsonPayload, RiotAccount.class);

        when(r4j.getAccountAPI()
                .getAccountByPUUID(any(), eq(puuid)))
                .thenReturn(r4jAccount);
    }

    @Etantdonnéque("l'API Riot ne renvoie aucun compte pour le nom {string} et le tag {string}")
    public void mockNoRiotNameTagAccount(String name, String tag) {
        when(r4j.getAccountAPI()
                .getAccountByTag(any(), eq(name), eq(tag)))
                .thenReturn(null);
    }

    @Etantdonnéque("l'API Riot renvoie le compte suivant pour le nom {string} et le tag {string} :")
    public void mockRiotNameTagAccount(String name, String tag, String jsonPayload) {
        RiotAccount r4jAccount = gson.fromJson(jsonPayload, RiotAccount.class);

        when(r4j.getAccountAPI()
                .getAccountByTag(any(), eq(name), eq(tag)))
                .thenReturn(r4jAccount);
    }

    @Etantdonnéque("l'API Riot renvoie la region suivante pour le compte associé au puuid {string} :")
    public void mockRiotRegionAccount(String puuid, String jsonPayload) {
        RiotAccountRegion r4jRegionAccount = gson.fromJson(jsonPayload, RiotAccountRegion.class);

        when(r4j.getAccountAPI()
                .getActiveRegion(any(), eq(ShardableGame.LOL), eq(puuid)))
                .thenReturn(r4jRegionAccount);
    }

    @Etantdonnéque("l'API Riot renvoie l'invocateur suivant pour le puuid {string} sur la région {string} :")
    public void mockRiotSummoner(String puuid, String region, String jsonPayload) {
        Summoner r4jSummoner = gson.fromJson(jsonPayload, Summoner.class);

        when(r4j.getLoLAPI()
                .getSummonerAPI()
                .getSummonerByPUUID(LeagueShard.valueOf(region), puuid))
                .thenReturn(r4jSummoner);
    }

    @Etantdonnéque("l'API Riot renvoie les maîtrises suivantes pour le puuid {string} :")
    public void mockRiotMasteries(String puuid, String jsonPayload) {
        List<ChampionMastery> r4jMasteries = gson.fromJson(
                jsonPayload,
                new TypeToken<List<ChampionMastery>>() {}.getType()
        );

        when(r4j.getLoLAPI()
                .getMasteryAPI()
                .getChampionMasteries(any(), eq(puuid)))
                .thenReturn(r4jMasteries);
    }

    @Etantdonnéque("l'API Riot renvoie les classements suivants pour le puuid {string} :")
    public void mockRiotRanks(String puuid, String jsonPayload) {
        List<LeagueEntry> r4jLeagueEntries = gson.fromJson(
                jsonPayload,
                new TypeToken<List<LeagueEntry>>() {}.getType()
        );

        when(r4j.getLoLAPI()
                .getLeagueAPI()
                .getLeagueEntriesByPUUID(any(), eq(puuid)))
                .thenReturn(r4jLeagueEntries);
    }
}