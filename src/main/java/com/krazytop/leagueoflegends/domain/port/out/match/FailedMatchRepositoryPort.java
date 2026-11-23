package com.krazytop.leagueoflegends.domain.port.out.match;

import com.krazytop.leagueoflegends.domain.model.match.FailedMatch;

import java.util.List;
import java.util.Set;

public interface FailedMatchRepositoryPort {

    /**
     * Identifie les matchs en échec déjà tentés d'être récupérés par l'invocateur.
     *
     * @param matchIds la liste des ids des matchs en échec à vérifier
     * @param puuid le puuid de l'invocateur
     * @return la liste des ids des matchs en échec déjà tentés d'être récupérés par l'invocateur
     */
    Set<String> findExistingFailedMatchIdsByOwner(List<String> matchIds, String puuid);

    /**
     * Met à jour le statut de récupération d'un match en échec pour un invocateur.
     * Utile dans le cas où un match a déjà été en échec pour un autre invocateur.
     *
     * @param matchId l'id du match en échec à mettre à jour
     * @param puuid le puuid de l'invocateur
     * @return true si le match en échec existait et a bien été mis à jour pour l'invocateur, false sinon
     */
    boolean addOwnerIfFailedMatchExists(String matchId, String puuid);

    /**
     * Sauvegarde un nouveau match en échec en BDD.
     *
     * @param failedMatch match en échec à sauvegarder
     */
    void saveFailedMatch(FailedMatch failedMatch);
}
