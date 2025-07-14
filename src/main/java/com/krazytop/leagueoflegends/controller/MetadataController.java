package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MetadataApi;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import com.krazytop.leagueoflegends.service.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetadataController implements MetadataApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataController.class);

    private final MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService){
        this.metadataService = metadataService;
    }

    @Override
    public ResponseEntity<MetadataDTO> getMetadata() {
        LOGGER.info("Retrieving metadata");
        MetadataDTO metadata = metadataService.getMetadataDTO();
        LOGGER.info("Metadata retrieved");
        return new ResponseEntity<>(metadata, HttpStatus.OK);
    }
}