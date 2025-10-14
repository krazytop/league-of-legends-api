package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MetadataApi;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import com.krazytop.leagueoflegends.service.MetadataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class MetadataController implements MetadataApi {

    private final MetadataService metadataService;

    @Override
    public ResponseEntity<MetadataDTO> getMetadata() {
        log.info("Retrieving metadata");
        MetadataDTO metadata = metadataService.getMetadataDTO();
        log.info("Metadata retrieved");
        return new ResponseEntity<>(metadata, HttpStatus.OK);
    }
}