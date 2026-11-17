package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MetadataDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetadataMongoRepository extends MongoRepository<MetadataDocument, String> {

    MetadataDocument findById(Integer id);
}
