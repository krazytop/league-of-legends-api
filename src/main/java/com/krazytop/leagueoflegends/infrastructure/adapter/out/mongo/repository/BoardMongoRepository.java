package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.BoardDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMongoRepository extends MongoRepository<BoardDocument, String> {
}