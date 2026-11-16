package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
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

    /**
     * Récupère les maîtrises de tous les champions d'un joueur
     *
     * @param puuid le puuid du joueur
     * @return la liste des maîtrises du joueur
     */
    public List<Mastery> getMasteries(String puuid) {
        return repositoryPort.findMasteries(puuid);
    }

    /**
     * Met à jour les maîtrises de tous les champions d'un joueur
     *
     * @param puuid le puuid du joueur
     */
    public void updateMasteries(String puuid) {
        List<Mastery> masteries = riotPort.fetchMasteries(EnumRegion.EUW1, puuid);//TODO region : à voir soit findSummoner en bdd soit api riot soit erreur
        repositoryPort.saveMasteries(masteries, puuid);
    }

}
