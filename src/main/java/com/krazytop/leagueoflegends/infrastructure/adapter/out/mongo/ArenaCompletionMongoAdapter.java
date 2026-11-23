package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.port.out.arenacompletion.ArenaCompletionRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.ArenaCompletionDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.ArenaCompletionMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.ArenaCompletionMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class ArenaCompletionMongoAdapter implements ArenaCompletionRepositoryPort {

    private final ArenaCompletionMongoRepository repository;
    private final ArenaCompletionMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    public Set<String> findArenaCompletion(String puuid) {
        return repository.findByPuuid(puuid)
                .map(ArenaCompletionDocument::getChampions)
                .orElse(new HashSet<>());
    }

    /**
     * {@inheritDoc}
     */
    public void saveArenaCompletion(Set<String> champions, String puuid) {
        repository.save(
                mapper.toDocument(puuid, champions));
    }
}
