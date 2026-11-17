package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Metadata;
import com.krazytop.leagueoflegends.domain.port.out.metadata.MetadataRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.MetadataMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.MetadataMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MetadataMongoAdapter implements MetadataRepositoryPort {

    private final MetadataMongoRepository repository;
    private final MetadataMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Metadata findMetadata() {
        return mapper.toModel(
                repository.findById(1));
    }
}
