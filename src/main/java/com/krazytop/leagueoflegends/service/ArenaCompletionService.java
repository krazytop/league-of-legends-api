package com.krazytop.leagueoflegends.service;

import com.krazytop.leagueoflegends.entity.*;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.ArenaCompletionMapper;
import com.krazytop.leagueoflegends.model.generated.ArenaCompletionDTO;
import com.krazytop.leagueoflegends.repository.ArenaCompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;

@Service
public class ArenaCompletionService {

    private final ArenaCompletionRepository arenaCompletionRepository;
    private final ArenaCompletionMapper arenaCompletionMapper;

    @Autowired
    public ArenaCompletionService(ArenaCompletionRepository arenaCompletionRepository, ArenaCompletionMapper arenaCompletionMapper) {
        this.arenaCompletionRepository = arenaCompletionRepository;
        this.arenaCompletionMapper = arenaCompletionMapper;
    }

    public ArenaCompletion getArenaCompletion(String puuid) {
        return arenaCompletionRepository.findByPuuid(puuid).orElseThrow(() -> new CustomException(ApiErrorEnum.SUMMONER_NEED_IMPORT_FIRST));
    }

    public ArenaCompletionDTO getArenaCompletionDTO(String puuid) {
        return arenaCompletionMapper.toDTO(getArenaCompletion(puuid));
    }

    public void createIfNeededSummonerArenaCompletion(String puuid) {
        if (arenaCompletionRepository.findByPuuid(puuid).isEmpty()) {
            arenaCompletionRepository.save(new ArenaCompletion().setPuuid(puuid));
        }
    }

    public void updateArenaCompletion(String puuid, Match match) {
        match.getTeams().stream()
                .filter(team -> team.getPlacement() == 1)
                .flatMap(team -> team.getParticipants().stream())
                .filter(participant -> puuid.equals(participant.getSummoner().getPuuid()))
                .findFirst()
                .ifPresent(winningSummonerParticipant -> {
                    createIfNeededSummonerArenaCompletion(puuid);
                    ArenaCompletion arenaCompletion = getArenaCompletion(puuid);
                    if (arenaCompletion.getChampions().add(winningSummonerParticipant.getChampion())) {
                        arenaCompletionRepository.save(arenaCompletion);
                    }
                });
    }

}
