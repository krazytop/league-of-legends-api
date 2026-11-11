package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.FailedMatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedMatchRepository extends MongoRepository<FailedMatch, String> {
}
