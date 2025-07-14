package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.ArenaCompletion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArenaCompletionRepository extends MongoRepository<ArenaCompletion, String> {

    Optional<ArenaCompletion> findByPuuid(String puuid);
}
