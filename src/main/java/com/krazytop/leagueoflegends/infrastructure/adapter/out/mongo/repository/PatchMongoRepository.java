package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference.PatchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatchMongoRepository extends MongoRepository<PatchDocument, String> {

    Optional<PatchDocument> findFirstByPatchIdAndLanguage(String patchId, String language);
}
