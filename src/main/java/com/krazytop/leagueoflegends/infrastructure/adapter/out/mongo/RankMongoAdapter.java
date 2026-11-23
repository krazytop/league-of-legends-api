package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.SeasonRanks;
import com.krazytop.leagueoflegends.domain.port.out.rank.RankRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.RankMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.RankMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class RankMongoAdapter implements RankRepositoryPort {

    private final RankMongoRepository repository;
    private final RankMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    public List<SeasonRanks> findRanks(String puuid) {
        return repository.findByPuuid(puuid)
                .map(ranksDocument -> mapper.toModels(ranksDocument.getSeasonOrSetRanks()))
                .orElse(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    public void saveRanks(List<SeasonRanks> ranks, String puuid) {
        repository.save(
                mapper.toDocument(puuid, ranks));
    }
}
