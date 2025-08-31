package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.ArenaCompletionApi;
import com.krazytop.leagueoflegends.model.generated.ArenaCompletionDTO;
import com.krazytop.leagueoflegends.service.ArenaCompletionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ArenaCompletionController implements ArenaCompletionApi {

    private final ArenaCompletionService arenaCompletionService;

    @Autowired
    public ArenaCompletionController(ArenaCompletionService arenaCompletionService) {
        this.arenaCompletionService = arenaCompletionService;
    }

    @Override
    public ResponseEntity<ArenaCompletionDTO> getArenaCompletion(String puuid) {
        log.info("Retrieving arena completion");
        ArenaCompletionDTO arenaCompletionDTO = arenaCompletionService.getArenaCompletionDTO(puuid);
        log.info("Arena completion retrieved");
        return new ResponseEntity<>(arenaCompletionDTO, HttpStatus.OK);
    }

}