package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.RankApi;
import com.krazytop.leagueoflegends.model.generated.RankDTO;
import com.krazytop.leagueoflegends.service.RankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class RankController implements RankApi {

    private final RankService rankService;

    @Override
    public ResponseEntity<RankDTO> getRanks(String puuid) {
        log.info("Retrieving ranks");
        RankDTO rank = rankService.getRanksDTO(puuid);
        log.info("Ranks retrieved");
        return new ResponseEntity<>(rank, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateRanks(String puuid) {
        log.info("Updating ranks");
        rankService.updateRanks(puuid);
        log.info("Ranks updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}