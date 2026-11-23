package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.domain.port.out.mastery.MasteryRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.mastery.MasteryRiotPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MasteryService {

    private final MasteryRiotPort riotPort;
    private final MasteryRepositoryPort repositoryPort;
    private final SummonerService summonerService;

    /**
     * Récupère les maîtrises de tous les champions d'un invocateur.
     *
     * @param puuid le puuid de l'invocateur
     * @return la liste des maîtrises de l'invocateur
     */
    public List<Mastery> getMasteries(String puuid) {
        return repositoryPort.findMasteries(puuid);
    }

    /**
     * Met à jour les maîtrises de tous les champions d'un invocateur.
     *
     * @param puuid le puuid de l'invocateur
     */
    public void updateMasteries(String puuid) {
        EnumRegion region = summonerService.getSummonerRegion(puuid);
        List<Mastery> masteries = riotPort.fetchMasteries(region, puuid);
        repositoryPort.saveMasteries(masteries, puuid);
    }

}
