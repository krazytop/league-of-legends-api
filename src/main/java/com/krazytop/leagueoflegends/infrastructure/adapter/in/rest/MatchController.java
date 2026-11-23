package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.MatchApi;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.MatchRestMapper;
import com.krazytop.leagueoflegends.model.generated.MatchPageDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchRequestDTO;
import com.krazytop.leagueoflegends.domain.service.MatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class MatchController implements MatchApi {

    private final MatchService service;
    private final MatchRestMapper mapper;

    @Override
    public ResponseEntity<MatchPageDTO> searchMatches(MatchSearchRequestDTO request) {
        log.info("Récupération des matchs avec les critères suivant : {}", request.toString());
        MatchPageDTO page = mapper.toDTO(
                service.searchMatches(
                        mapper.toModel(request)));
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<Void> updateMatches(String puuid) {
        log.info("Mis à jour des matchs pour le puuid {}", puuid);
        service.updateMatches(puuid, false);
        return ResponseEntity.ok().build();
    }
}