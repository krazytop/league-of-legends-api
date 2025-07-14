package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.RankApi;
import com.krazytop.leagueoflegends.model.generated.RankDTO;
import com.krazytop.leagueoflegends.service.RankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankController implements RankApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankController.class);

    private final RankService rankService;

    @Autowired
    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @Override
    public ResponseEntity<RankDTO> getRanks(String puuid) {
        LOGGER.info("Retrieving ranks");
        RankDTO rank = rankService.getRanksDTO(puuid);
        LOGGER.info("Ranks retrieved");
        return new ResponseEntity<>(rank, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateRanks(String puuid) {
        LOGGER.info("Updating ranks");
        rankService.updateRanks(puuid);
        LOGGER.info("Ranks updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}