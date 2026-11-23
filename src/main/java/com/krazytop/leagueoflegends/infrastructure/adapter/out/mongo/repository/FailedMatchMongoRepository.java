package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.FailedMatchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FailedMatchMongoRepository extends MongoRepository<FailedMatchDocument, String> {
}
