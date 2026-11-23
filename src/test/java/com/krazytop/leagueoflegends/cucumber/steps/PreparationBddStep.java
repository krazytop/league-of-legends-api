package com.krazytop.leagueoflegends.cucumber.steps;

import com.fasterxml.jackson.databind.JavaType;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.SummonerDocument;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Etantdonnéqu;
import io.cucumber.java.fr.Etantdonnéque;

import java.util.List;

public class PreparationBddStep extends LeagueOfLegendsIT {

    @Before
    public void resetDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Etantdonnéque("le summoner {string} est sur la region {}")
    public void insertSummonerWithRegion(String puuid, EnumRegion region) {
        mongoTemplate.insert(new SummonerDocument().setPuuid(puuid).setRegion(region), "Summoner");
    }

    @Etantdonnéqu("on insère dans la collection {string} le document suivant :")
    public void insertSingleDocumentInCollection(String collectionName, String jsonPayload) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        Object document = objectMapper.readValue(jsonPayload, documentClass);

        mongoTemplate.insert(document, collectionName);
    }

    @Etantdonnéque("on insère dans la collection {string} les documents suivants :")
    public void insertDocumentsInCollection(String collectionName, String jsonPayload) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        jsonPayload = cucumberContext.resolveDynamicData(jsonPayload);

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, documentClass);
        List<?> documents = objectMapper.readValue(jsonPayload, listType);

        mongoTemplate.insert(documents, collectionName);
    }
}