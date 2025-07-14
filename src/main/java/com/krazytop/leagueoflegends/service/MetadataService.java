package com.krazytop.leagueoflegends.service;

import com.krazytop.leagueoflegends.entity.Metadata;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.MetadataMapper;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import com.krazytop.leagueoflegends.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;
    private final MetadataMapper metadataMapper;

    @Autowired
    public MetadataService(MetadataRepository metadataRepository, MetadataMapper  metadataMapper) {
        this.metadataRepository = metadataRepository;
        this.metadataMapper = metadataMapper;
    }

    public MetadataDTO getMetadataDTO() {
        return getMetadata()
                .map(this.metadataMapper::toDTO)
                .orElseThrow(() -> new CustomException(ApiErrorEnum.METADATA_NOT_FOUND));
    }

    public Optional<Metadata> getMetadata() {
        return metadataRepository.findAll().stream().findFirst();
    }

    public void saveMetadata(Metadata metadata) {
        metadataRepository.save(metadata);
    }

}
