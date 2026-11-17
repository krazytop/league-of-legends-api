package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MasteriesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MasteryMongoRepository extends MongoRepository<MasteriesDocument, String> {

    Optional<MasteriesDocument> findByPuuid(String puuid);
}
