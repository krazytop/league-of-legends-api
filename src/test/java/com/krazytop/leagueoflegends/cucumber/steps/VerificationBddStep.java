package com.krazytop.leagueoflegends.cucumber.steps;

import com.fasterxml.jackson.databind.JavaType;
import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.fr.Alors;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationBddStep extends LeagueOfLegendsIT {

    @Alors("la collection {string} est vide")
    public void verifyCollectionIsEmpty(String collectionName) {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        List<?> actualDocuments = mongoTemplate.findAll(documentClass, collectionName);

        assertThat(actualDocuments).isEmpty();
    }

    @Alors("la collection {string} contient {int} documents")
    @Alors("la collection {string} contient {int} document")
    public void verifyCollectionHasDocumentCount(String collectionName, int expectedDocumentsCount) {
        long actualDocumentsCount = mongoTemplate.count(new Query(), collectionName);

        assertThat(actualDocumentsCount).isEqualTo(expectedDocumentsCount);
    }

    @Alors("la collection {string} contient uniquement le document suivant :")
    public void verifyCollectionContainsOnlySingleDocument(String collectionName, String expectedJson) throws Exception {
        Class<?> documentClass = collectionRegistry.resolveClass(collectionName);
        expectedJson = cucumberContext.resolveDynamicData(expectedJson);
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
        expectedJson = cucumberContext.resolveDynamicData(expectedJson);

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, documentClass);
        List<?> expectedDocuments = objectMapper.readValue(expectedJson, listType);

        List<?> actualDocuments = mongoTemplate.findAll(documentClass, collectionName);

        assertThat(actualDocuments.get(1)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedDocuments.get(1));

        assertThat(actualDocuments)
                .hasSameSizeAs(expectedDocuments)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedDocuments);
    }
}