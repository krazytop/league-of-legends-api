package com.krazytop.leagueoflegends.domain.port.out.arenacompletion;

import java.util.Set;

public interface ArenaCompletionRepositoryPort {

    /**
     * Récupère les champions avec lesquels un invocateur a gagné en Arena depuis la BDD.
     *
     * @param puuid le puuid de l'invocateur
     * @return la liste des champions
     */
    Set<String> findArenaCompletion(String puuid);

    /**
     * Met à jour les champions avec lesquels un invocateur a gagné en Arena en BDD.
     *
     * @param puuid le puuid de l'invocateur
     */
    void saveArenaCompletion(Set<String> champions, String puuid);
}
