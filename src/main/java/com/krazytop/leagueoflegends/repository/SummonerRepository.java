package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.Summoner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface SummonerRepository extends MongoRepository<Summoner, String> {

    @Query("{'tag' : {$regex : '^?0$', $options : 'i'}, 'name' : {$regex : '^?1$', $options : 'i'}}")
    Optional<Summoner> findFirstByTagAndName(String tag, String name);

    Optional<Summoner> findFirstByPuuid(String puuid);

}
