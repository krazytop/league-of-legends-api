package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.reference.Patch;
import com.krazytop.leagueoflegends.domain.port.out.patch.PatchRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.PatchMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.PatchMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatchMongoAdapter implements PatchRepositoryPort {

    private final PatchMongoRepository repository;
    private final PatchMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    public Optional<Patch> findPatch(String patchId, String language) {
        return repository.findFirstByPatchIdAndLanguage(patchId, language)
                .map(mapper::toModel);
    }
}
