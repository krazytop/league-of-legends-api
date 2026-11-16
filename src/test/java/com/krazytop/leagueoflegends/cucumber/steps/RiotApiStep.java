package com.krazytop.leagueoflegends.cucumber.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.fr.Etantdonnéque;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RiotApiStep extends LeagueOfLegendsIT {

    @Etantdonnéque("l'API Riot renvoie les maîtrises suivantes pour le puuid {string} :")
    public void mockRiotMasteries(String puuid, String jsonPayload) throws Exception {
        List<ChampionMastery> r4jMasteries = objectMapper.readValue(
                jsonPayload,
                new TypeReference<>() {
                }
        );

        when(r4j.getLoLAPI()
                .getMasteryAPI()
                .getChampionMasteries(any(), eq(puuid)))
                .thenReturn(r4jMasteries);
    }
}
