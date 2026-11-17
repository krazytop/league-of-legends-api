package com.krazytop.leagueoflegends.domain.port.out.mastery;

import com.krazytop.leagueoflegends.domain.model.Mastery;

import java.util.List;

public interface MasteryRepositoryPort {

    /**
     * Récupère les maîtrises de tous les champions d'un invocateur depuis la BDD
     *
     * @param puuid le puuid de l'invocateur
     * @return la liste des maîtrises de l'invocateur
     */
    List<Mastery> findMasteries(String puuid);

    /**
     * Met à jour les maîtrises de tous les champions d'un invocateur en BDD
     *
     * @param puuid le puuid de l'invocateur
     */
    void saveMasteries(List<Mastery> masteries, String puuid);
}
