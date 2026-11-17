package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.SummonerApi;
import com.krazytop.leagueoflegends.domain.service.SummonerService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.SummonerRestMapper;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class SummonerController implements SummonerApi {

    private final SummonerService service;
    private final SummonerRestMapper mapper;

    @Override
    public ResponseEntity<SummonerDTO> getSummonerByTagAndName(String tag, String name) {
        log.info("Récupération de l'invocateur {} #{} ", name, tag);
        SummonerDTO summoner = mapper.toDTO(
                service.getSummoner(tag, name));
        return ResponseEntity.ok(summoner);
    }

    @Override
    public ResponseEntity<SummonerDTO> getSummonerByPuuid(String puuid) {
        log.info("Récupération de l'invocateur pour le puuid {}", puuid);
        SummonerDTO summoner = mapper.toDTO(
                service.getSummoner(puuid));
        return ResponseEntity.ok(summoner);
    }

    @Override
    public ResponseEntity<SummonerDTO> updateSummoner(String puuid) {
        log.info("Mise à jour de l'invocateur pour le puuid {}", puuid);
        SummonerDTO summoner = mapper.toDTO(
                service.updateSummoner(puuid));
        return ResponseEntity.ok(summoner);
    }
}