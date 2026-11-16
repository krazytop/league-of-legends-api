package com.krazytop.leagueoflegends.cucumber.config;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MasteriesDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MetadataDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.RanksDocument;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MongoCollectionRegistry {

    private static final Map<String, Class<?>> COLLECTIONS = Map.of(
            "Mastery", MasteriesDocument.class,
            "Metadata", MetadataDocument.class,
            "Rank", RanksDocument.class
    );

    public Class<?> resolveClass(String collectionName) {
        Class<?> clazz = COLLECTIONS.get(collectionName);
        if (clazz == null) {
            throw new IllegalArgumentException(
                    "Collection inconnue dans MongoCollectionRegistry : '" + collectionName + "'"
            );
        }
        return clazz;
    }
}