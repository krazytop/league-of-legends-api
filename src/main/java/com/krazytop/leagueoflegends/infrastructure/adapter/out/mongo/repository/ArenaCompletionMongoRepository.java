package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.ArenaCompletionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArenaCompletionMongoRepository extends MongoRepository<ArenaCompletionDocument, String> {

    Optional<ArenaCompletionDocument> findByPuuid(String puuid);
}
