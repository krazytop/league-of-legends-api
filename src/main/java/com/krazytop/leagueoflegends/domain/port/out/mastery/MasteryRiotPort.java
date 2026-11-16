package com.krazytop.leagueoflegends.domain.port.out.mastery;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Mastery;

import java.util.List;

public interface MasteryRiotPort {

    /**
     * Récupère les maîtrises de tous les champions d'un joueur depuis l'api de RIOT
     *
     * @param region la région du joueur
     * @param puuid le puuid du joueur
     * @return la liste des maîtrises du joueur
     */
    List<Mastery> fetchMasteries(EnumRegion region, String puuid);
}
