package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.RankApi;
import com.krazytop.leagueoflegends.domain.service.RankService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.RankRestMapper;
import com.krazytop.leagueoflegends.model.generated.SeasonOrSetRanksDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class RankController implements RankApi {

    private final RankService service;
    private final RankRestMapper mapper;

    @Override
    public ResponseEntity<List<SeasonOrSetRanksDTO>> getRanks(String puuid) {
        log.info("Récupération des classements pour le puuid {}", puuid);
        List<SeasonOrSetRanksDTO> ranks = service.getRanks(puuid).stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(ranks);
    }

    @Override
    public ResponseEntity<Void> updateRanks(String puuid) {
        log.info("Mise à jour des classements pour le puuid {}", puuid);
        service.updateRanks(puuid);
        return ResponseEntity.ok().build();
    }
}