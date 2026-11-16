package com.krazytop.leagueoflegends.cucumber.steps;

import com.fasterxml.jackson.databind.JavaType;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Etantdonnéque;

import java.util.List;

public class PreparationBddStep extends LeagueOfLegendsIT {

    @Before
    public void resetDatabase() {
        mongoTemplate.getDb().drop();//TODO il faudra drop collection par collection en excluant les nomenclatures
    }

    @Etantdonnéque("la collection {string} contient le document suivant :")
    public void insertSingleDocumentInCollection(String collectionName, String jsonPayload) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        Object document = objectMapper.readValue(jsonPayload, documentClass);

        mongoTemplate.insert(document, collectionName);
    }

    @Etantdonnéque("la collection {string} contient les documents suivants :")
    public void insertDocumentsInCollection(String collectionName, String jsonPayload) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, documentClass);
        List<?> documents = objectMapper.readValue(jsonPayload, listType);

        mongoTemplate.insert(documents, collectionName);
    }
}