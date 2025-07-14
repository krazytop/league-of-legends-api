package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MasteryApi;
import com.krazytop.leagueoflegends.model.generated.MasteriesDTO;
import com.krazytop.leagueoflegends.service.MasteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasteryController implements MasteryApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasteryController.class);

    private final MasteryService masteryService;

    @Autowired
    public MasteryController(MasteryService masteryService) {
        this.masteryService = masteryService;
    }

    @Override
    public ResponseEntity<MasteriesDTO> getMasteries(String puuid) {
        LOGGER.info("Retrieving masteries");
        MasteriesDTO masteriesDTO = masteryService.getMasteriesDTO(puuid);
        LOGGER.info("Masteries retrieved");
        return new ResponseEntity<>(masteriesDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateMasteries(String puuid) {
        LOGGER.info("Updating masteries");
        masteryService.updateMasteries(puuid);
        LOGGER.info("Masteries updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}