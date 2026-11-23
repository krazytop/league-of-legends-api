package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.ArenaCompletionApi;
import com.krazytop.leagueoflegends.domain.service.ArenaCompletionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
public class ArenaCompletionController implements ArenaCompletionApi {

    private final ArenaCompletionService service;

    @Override
    public ResponseEntity<Set<String>> getArenaCompletion(String puuid) {
        log.info("Récupération des achèvements d'Arena pour le puuid {}", puuid);
        Set<String> champions = service.getArenaCompletion(puuid);
        return ResponseEntity.ok(champions);
    }

}