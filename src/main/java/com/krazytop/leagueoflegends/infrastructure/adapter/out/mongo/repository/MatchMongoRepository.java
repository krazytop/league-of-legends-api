package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.MatchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchMongoRepository extends MongoRepository<MatchDocument, String> {
}
