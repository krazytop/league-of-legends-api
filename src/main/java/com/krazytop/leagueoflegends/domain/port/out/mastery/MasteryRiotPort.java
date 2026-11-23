package com.krazytop.leagueoflegends.domain.port.out.mastery;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Mastery;

import java.util.List;

public interface MasteryRiotPort {

    /**
     * Récupère les maîtrises de tous les champions d'un invocateur depuis l'api de Riot.
     *
     * @param region la région de l'invocateur
     * @param puuid le puuid de l'invocateur
     * @return la liste des maîtrises de l'invocateur
     */
    List<Mastery> fetchMasteries(EnumRegion region, String puuid);
}
