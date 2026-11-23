package com.krazytop.leagueoflegends.cucumber.config;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.*;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.FailedMatchDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.MatchDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference.PatchDocument;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MongoCollectionRegistry {

    private static final Map<String, Class<?>> COLLECTIONS = Map.of(
            "ArenaCompletion", ArenaCompletionDocument.class,
            "Board", BoardDocument.class,
            "FailedMatch", FailedMatchDocument.class,
            "Mastery", MasteriesDocument.class,
            "Match", MatchDocument.class,
            "Metadata", MetadataDocument.class,
            "Patch", PatchDocument.class,
            "Rank", RanksDocument.class,
            "Summoner", SummonerDocument.class
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