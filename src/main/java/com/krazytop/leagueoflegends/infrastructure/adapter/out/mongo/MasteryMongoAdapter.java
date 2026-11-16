package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.domain.port.out.mastery.MasteryRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.MasteryMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.MasteryMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MasteryMongoAdapter implements MasteryRepositoryPort {

    private final MasteryMongoRepository repository;
    private final MasteryMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    public List<Mastery> findMasteries(String puuid) {
        return repository.findByPuuid(puuid)
                .map(masteriesDocument -> mapper.toModels(masteriesDocument.getChampions()))
                .orElse(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    public void saveMasteries(List<Mastery> masteries, String puuid) {
        repository.save(
                mapper.toDocument(puuid, masteries));
    }
}
