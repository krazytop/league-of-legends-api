package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MasteryApi;
import com.krazytop.leagueoflegends.model.generated.MasteriesDTO;
import com.krazytop.leagueoflegends.service.MasteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MasteryController implements MasteryApi {

    private final MasteryService masteryService;

    @Autowired
    public MasteryController(MasteryService masteryService) {
        this.masteryService = masteryService;
    }

    @Override
    public ResponseEntity<MasteriesDTO> getMasteries(String puuid) {
        log.info("Retrieving masteries");
        MasteriesDTO masteriesDTO = masteryService.getMasteriesDTO(puuid);
        log.info("Masteries retrieved");
        return new ResponseEntity<>(masteriesDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateMasteries(String puuid) {
        log.info("Updating masteries");
        masteryService.updateMasteries(puuid);
        log.info("Masteries updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}