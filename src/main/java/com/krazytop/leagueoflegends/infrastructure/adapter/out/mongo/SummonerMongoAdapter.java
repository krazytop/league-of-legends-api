package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.port.out.summoner.SummonerRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.SummonerMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.SummonerMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class SummonerMongoAdapter implements SummonerRepositoryPort {

    private final SummonerMongoRepository repository;
    private final SummonerMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Summoner> findSummoner(String puuid) {
        return repository.findByPuuid(puuid)
                .map(mapper::toModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Summoner> findSummoner(String tag, String name) {
        return repository.findFirstByTagAndName(tag, name)
                .map(mapper::toModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Summoner saveSummoner(Summoner summoner) {
        return mapper.toModel(
                repository.save(
                        mapper.toDocument(summoner)));
    }
}
