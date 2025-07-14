package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.ArenaCompletionApi;
import com.krazytop.leagueoflegends.model.generated.ArenaCompletionDTO;
import com.krazytop.leagueoflegends.service.ArenaCompletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArenaCompletionController implements ArenaCompletionApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArenaCompletionController.class);

    private final ArenaCompletionService arenaCompletionService;

    @Autowired
    public ArenaCompletionController(ArenaCompletionService arenaCompletionService) {
        this.arenaCompletionService = arenaCompletionService;
    }

    @Override
    public ResponseEntity<ArenaCompletionDTO> getArenaCompletion(String puuid) {
        LOGGER.info("Retrieving arena completion");
        ArenaCompletionDTO arenaCompletionDTO = arenaCompletionService.getArenaCompletionDTO(puuid);
        LOGGER.info("Arena completion retrieved");
        return new ResponseEntity<>(arenaCompletionDTO, HttpStatus.OK);
    }

}