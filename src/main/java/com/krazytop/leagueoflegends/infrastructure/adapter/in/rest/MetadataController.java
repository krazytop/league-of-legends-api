package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.MetadataApi;
import com.krazytop.leagueoflegends.domain.service.MetadataService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.MetadataRestMapper;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class MetadataController implements MetadataApi {

    private final MetadataService service;
    private final MetadataRestMapper mapper;

    @Override
    public ResponseEntity<MetadataDTO> getMetadata() {
        log.info("Récupération des métadonnées actuelles de League of Legends");
        MetadataDTO metadata = mapper.toDTO(
                service.getMetadata());
        return ResponseEntity.ok(metadata);
    }
}