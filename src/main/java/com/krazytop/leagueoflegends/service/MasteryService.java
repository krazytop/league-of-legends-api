package com.krazytop.leagueoflegends.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krazytop.leagueoflegends.entity.Masteries;
import com.krazytop.leagueoflegends.entity.Mastery;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.MasteryMapper;
import com.krazytop.leagueoflegends.model.generated.MasteriesDTO;
import com.krazytop.leagueoflegends.repository.MasteryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MasteryService {

    private final MasteryRepository masteryRepository;
    private final SummonerService summonerService;
    private final MasteryMapper masteryMapper;

    @Value("${league-of-legends.api-key:api-key}")
    private String apiKey;

    public Masteries getMasteries(String puuid) {
        return masteryRepository.findByPuuid(puuid).orElseThrow(() -> new CustomException(ApiErrorEnum.SUMMONER_NEED_IMPORT_FIRST));
    }

    public MasteriesDTO getMasteriesDTO(String puuid) {
        return masteryMapper.toDTO(getMasteries(puuid));
    }

    public void updateMasteries(String puuid) {
        try {
            String region = summonerService.getLocalSummoner(puuid).orElseThrow(() -> new CustomException(ApiErrorEnum.SUMMONER_NEED_IMPORT_FIRST)).getRegion();
            String stringUrl = String.format("https://%s.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/%s?api_key=%s", region, puuid, apiKey);
            ObjectMapper mapper = new ObjectMapper();
            List<JsonNode> nodes = mapper.convertValue(mapper.readTree(new URI(stringUrl).toURL()), new TypeReference<>() {});
            Masteries masteries = new Masteries(nodes.getFirst().get("puuid").asText());
            nodes.forEach(node -> masteries.getChampions().add(mapper.convertValue(node, Mastery.class)));
            masteryRepository.save(masteries);
        } catch (IOException | URISyntaxException ex) {
            throw new CustomException(ApiErrorEnum.MASTERY_UPDATE_ERROR, ex);
        }
    }

}
