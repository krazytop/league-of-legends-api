package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MatchApi;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchController implements MatchApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }

    @Override
    public ResponseEntity<List<MatchDTO>> getMatches(String puuid, Integer pageNb, String queue, String role) {
        LOGGER.info("Retrieving matches");
        List<MatchDTO> matches = matchService.getMatches(puuid, pageNb, queue, role);
        LOGGER.info("Matches retrieved");
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getMatchesCount(String puuid, String queue, String role) {
        LOGGER.info("Retrieving matches count");
        Integer matches = matchService.getMatchesCount(puuid, queue, role);
        LOGGER.info("Matches count retrieved");
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateMatches(String puuid) {
        LOGGER.info("Updating matches");
        matchService.updateMatches(puuid);
        LOGGER.info("Matches updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}