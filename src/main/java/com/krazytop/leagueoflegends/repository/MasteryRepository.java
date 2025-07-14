package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.Masteries;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MasteryRepository extends MongoRepository<Masteries, String> {

    Optional<Masteries> findByPuuid(String puuid);
}
