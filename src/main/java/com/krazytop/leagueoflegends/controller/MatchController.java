package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.MatchApi;
import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.entity.MatchSearchCriteria;
import com.krazytop.leagueoflegends.entity.PageableSearchCriteria;
import com.krazytop.leagueoflegends.mapper.MatchMapper;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchRequestDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchResponseDTO;
import com.krazytop.leagueoflegends.service.MatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class MatchController implements MatchApi {

    private final MatchMapper mapper;
    private final MatchService service;

    @Override
    public ResponseEntity<MatchSearchResponseDTO> getMatches(MatchSearchRequestDTO requestDTO) {
        log.info("Retrieving matches");
        PageableSearchCriteria<MatchSearchCriteria> requestModel = mapper.toModel(requestDTO);
        Page<Match> responseModel = service.getMatches(requestModel);
        log.info("Matches retrieved");
        return ResponseEntity.ok(mapper.toDTO(responseModel));
    }

    @Override
    public ResponseEntity<List<MatchDTO>> getMatchess(String puuid, Integer pageNb, String queue, String role) {
        log.info("Retrieving matches");
        List<MatchDTO> matches = service.getMatches(puuid, pageNb, queue, role);
        log.info("Matches retrieved");
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getMatchesCount(String puuid, String queue, String role) {
        log.info("Retrieving matches count");
        Integer matches = service.getMatchesCount(puuid, queue, role);
        log.info("Matches count retrieved");
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateMatches(String puuid) {
        log.info("Updating matches");
        service.updateMatches(puuid);
        log.info("Matches updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}