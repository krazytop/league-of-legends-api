package com.krazytop.leagueoflegends.cucumber.steps;

import com.fasterxml.jackson.databind.JavaType;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.fr.Alors;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationBddStep extends LeagueOfLegendsIT {

    @Alors("la collection {string} est vide")
    public void verifyCollectionIsEmpty(String collectionName) {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        List<?> actualDocuments = mongoTemplate.findAll(documentClass, collectionName);

        assertThat(actualDocuments).isEmpty();
    }

    @Alors("la collection {string} contient uniquement le document suivant:")
    public void verifyCollectionContainsOnlySingleDocument(String collectionName, String expectedJson) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        Object expectedDocument = objectMapper.readValue(expectedJson, documentClass);

        List<?> actualDocuments = mongoTemplate.findAll(documentClass, collectionName);

        assertThat(actualDocuments)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedDocument);
    }

    @Alors("la collection {string} contient uniquement les documents suivants :")
    public void verifyCollectionContainsOnly(String collectionName, String expectedJson) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, documentClass);
        List<?> expectedDocuments = objectMapper.readValue(expectedJson, listType);

        List<?> actualDocuments = mongoTemplate.findAll(documentClass, collectionName);

        // Vérifie la taille exacte ET la correspondance profonde de tous les champs
        assertThat(actualDocuments)
                .hasSameSizeAs(expectedDocuments)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedDocuments);
    }
}