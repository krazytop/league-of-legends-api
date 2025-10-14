package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.ArenaCompletionApi;
import com.krazytop.leagueoflegends.model.generated.ArenaCompletionDTO;
import com.krazytop.leagueoflegends.service.ArenaCompletionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class ArenaCompletionController implements ArenaCompletionApi {

    private final ArenaCompletionService arenaCompletionService;

    @Override
    public ResponseEntity<ArenaCompletionDTO> getArenaCompletion(String puuid) {
        log.info("Retrieving arena completion");
        ArenaCompletionDTO arenaCompletionDTO = arenaCompletionService.getArenaCompletionDTO(puuid);
        log.info("Arena completion retrieved");
        return new ResponseEntity<>(arenaCompletionDTO, HttpStatus.OK);
    }

}