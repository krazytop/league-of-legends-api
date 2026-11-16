package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.MasteryApi;
import com.krazytop.leagueoflegends.domain.service.MasteryService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.MasteryRestMapper;
import com.krazytop.leagueoflegends.model.generated.MasteryDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class MasteryController implements MasteryApi {

    private final MasteryService service;
    private final MasteryRestMapper mapper;

    @Override
    public ResponseEntity<List<MasteryDTO>> getMasteries(String puuid) {
        log.info("Récupération des maîtrises de champions pour le puuid {}", puuid);
        List<MasteryDTO> masteries = service.getMasteries(puuid).stream()
                .map(mapper::toDTO)
                .toList();
        return new ResponseEntity<>(masteries, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateMasteries(String puuid) {
        log.info("Mise à jour des maîtrises de champions pour le puuid {}", puuid);
        service.updateMasteries(puuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}