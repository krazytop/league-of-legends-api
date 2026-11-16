package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.RanksDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RankMongoRepository extends MongoRepository<RanksDocument, String> {

    Optional<RanksDocument> findByPuuid(String puuid);
}
