package com.krazytop.leagueoflegends.service;

import com.krazytop.leagueoflegends.entity.Metadata;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.MetadataMapper;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import com.krazytop.leagueoflegends.repository.MetadataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;
    private final MetadataMapper metadataMapper;

    public MetadataDTO getMetadataDTO() {
        return getMetadata()
                .map(this.metadataMapper::toDTO)
                .orElseThrow(() -> new CustomException(ApiErrorEnum.METADATA_NOT_FOUND));
    }

    public Optional<Metadata> getMetadata() {
        return metadataRepository.findAll().stream().findFirst();
    }

}
