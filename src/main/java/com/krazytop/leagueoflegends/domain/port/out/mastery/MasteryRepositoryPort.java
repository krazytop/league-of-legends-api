package com.krazytop.leagueoflegends.domain.port.out.mastery;

import com.krazytop.leagueoflegends.domain.model.Mastery;

import java.util.List;

public interface MasteryRepositoryPort {

    /**
     * Récupère les maîtrises de tous les champions d'un joueur depuis la BDD
     *
     * @param puuid le puuid du joueur
     * @return la liste des maîtrises du joueur
     */
    List<Mastery> findMasteries(String puuid);

    /**
     * Met à jour les maîtrises de tous les champions d'un joueur en BDD
     *
     * @param puuid le puuid du joueur
     */
    void saveMasteries(List<Mastery> masteries, String puuid);
}
