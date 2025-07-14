package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.SummonerApi;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import com.krazytop.leagueoflegends.service.SummonerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummonerController implements SummonerApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerController.class);

    private final SummonerService summonerService;

    @Autowired
    public SummonerController(SummonerService summonerService){
        this.summonerService = summonerService;
    }

    @Override
    public ResponseEntity<SummonerDTO> getSummonerByTagAndName(String tag, String name) {
        LOGGER.info("Retrieving summoner with name and tag");
        SummonerDTO summoner = summonerService.getSummonerDTO(tag, name);
        LOGGER.info("Summoner retrieved");
        return new ResponseEntity<>(summoner, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SummonerDTO> getSummonerByPuuid(String puuid) {
        LOGGER.info("Retrieving summoner with puuid");
        SummonerDTO summoner = summonerService.getSummonerDTO(puuid);
        LOGGER.info("Summoner retrieved");
        return new ResponseEntity<>(summoner, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SummonerDTO> updateSummoner(String puuid) {
        LOGGER.info("Updating summoner");
        SummonerDTO summoner = summonerService.updateSummoner(puuid);
        LOGGER.info("Summoner updated");
        return new ResponseEntity<>(summoner, HttpStatus.OK);
    }
}