package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.SummonerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SummonerMongoRepository extends MongoRepository<SummonerDocument, String> {

    Optional<SummonerDocument> findByPuuid(String puuid);

    Optional<SummonerDocument> findFirstByTagAndName(String tag, String name);
}
