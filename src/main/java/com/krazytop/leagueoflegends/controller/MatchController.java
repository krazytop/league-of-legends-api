package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MatchApi;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.service.MatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class MatchController implements MatchApi {

    private final MatchService matchService;

    @Override
    public ResponseEntity<List<MatchDTO>> getMatches(String puuid, Integer pageNb, String queue, String role) {
        log.info("Retrieving matches");
        List<MatchDTO> matches = matchService.getMatches(puuid, pageNb, queue, role);
        log.info("Matches retrieved");
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getMatchesCount(String puuid, String queue, String role) {
        log.info("Retrieving matches count");
        Integer matches = matchService.getMatchesCount(puuid, queue, role);
        log.info("Matches count retrieved");
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateMatches(String puuid) {
        log.info("Updating matches");
        matchService.updateMatches(puuid);
        log.info("Matches updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}