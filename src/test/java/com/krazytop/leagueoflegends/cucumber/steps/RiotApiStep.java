package com.krazytop.leagueoflegends.cucumber.steps;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.After;
import io.cucumber.java.fr.Etantdonnéque;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.api.regions.ShardableGame;
import no.stelar7.api.r4j.basic.utils.Utils;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import no.stelar7.api.r4j.pojo.shared.GAMHSMatch;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import no.stelar7.api.r4j.pojo.shared.RiotAccountRegion;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RiotApiStep extends LeagueOfLegendsIT {

    private final Gson gson = Utils.getGson();
    // Attention pour l'instant on ne peut pas cumuler des matchs en succès et en erreur
    private MockedConstruction<MatchBuilder> matchBuilderMock;

    @Etantdonnéque("l'API Riot ne renvoie aucun compte pour le puuid {string}")
    public void mockNoRiotPuuidAccount(String puuid) {
        when(r4j.getAccountAPI()
                .getAccountByPUUID(RegionShard.EUROPE, puuid))
                .thenReturn(null);
    }

    @Etantdonnéque("l'API Riot renvoie le compte suivant pour le puuid {string} :")
    public void mockRiotPuuidAccount(String puuid, String jsonPayload) {
        RiotAccount r4jAccount = gson.fromJson(jsonPayload, RiotAccount.class);

        when(r4j.getAccountAPI()
                .getAccountByPUUID(RegionShard.EUROPE, puuid))
                .thenReturn(r4jAccount);
    }

    @Etantdonnéque("l'API Riot ne renvoie aucun compte pour le nom {string} et le tag {string}")
    public void mockNoRiotNameTagAccount(String name, String tag) {
        when(r4j.getAccountAPI()
                .getAccountByTag(RegionShard.EUROPE, name, tag))
                .thenReturn(null);
    }

    @Etantdonnéque("l'API Riot renvoie le compte suivant pour le nom {string} et le tag {string} :")
    public void mockRiotNameTagAccount(String name, String tag, String jsonPayload) {
        RiotAccount r4jAccount = gson.fromJson(jsonPayload, RiotAccount.class);

        when(r4j.getAccountAPI()
                .getAccountByTag(RegionShard.EUROPE, name, tag))
                .thenReturn(r4jAccount);
    }

    @Etantdonnéque("l'API Riot renvoie la region suivante pour le compte associé au puuid {string} :")
    public void mockRiotRegionAccount(String puuid, String jsonPayload) {
        RiotAccountRegion r4jRegionAccount = gson.fromJson(jsonPayload, RiotAccountRegion.class);

        when(r4j.getAccountAPI()
                .getActiveRegion(RegionShard.EUROPE, ShardableGame.LOL, puuid))
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

    @Etantdonnéque("l'API Riot renvoie la liste d'IDs de matchs suivante pour le puuid {string} :")
    public void mockRiotMatchIds(String puuid, String jsonPayload) {
        List<String> matchIds = gson.fromJson(
                jsonPayload,
                new TypeToken<List<String>>() {}.getType()
        );

        when(r4j.getLoLAPI()
                .getMatchAPI()
                .getMatchList(any(), eq(puuid), any(), any(), any(), any(), any(), any()))
                .thenReturn(matchIds);
    }

    @Etantdonnéque("l'API Riot renvoie le match {string} suivant :")
    public void mockRiotMatch(String matchId, String jsonPayload) {
        GAMHSMatch rawMatch = gson.fromJson(jsonPayload, GAMHSMatch.class);

        matchBuilderMock = Mockito.mockConstruction(MatchBuilder.class, (mock, context) -> {
            when(mock.withContext(any())).thenReturn(mock);
            when(mock.getMatchRAW()).thenReturn(rawMatch);
        });
    }

    @Etantdonnéque("l'API Riot renvoie une erreur pour le match {string}")
    public void mockRiotMatchError(String matchId) {
        matchBuilderMock = Mockito.mockConstruction(MatchBuilder.class, (mock, context) -> {
            String requestedMatchId = (String) context.arguments().get(1);
            when(mock.withContext(any())).thenReturn(mock);
            if (matchId.equals(requestedMatchId)) {
                when(mock.getMatchRAW()).thenThrow(new RuntimeException("500 Internal Server Error"));
            }
        });
    }

    @After
    public void closeMocks() {
        if (matchBuilderMock != null) {
            matchBuilderMock.close();
        }
    }
}