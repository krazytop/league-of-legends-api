package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.nomenclature.Patch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatchRepository extends MongoRepository<Patch, String> {

    Optional<Patch> findFirstByPatchIdAndLanguage(String patchId, String language);
}
