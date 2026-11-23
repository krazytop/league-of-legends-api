package com.krazytop.leagueoflegends.domain.port.out.match;

import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MatchRepositoryPort {

    /**
     * Récupère les matchs suivant plusieurs critères depuis la BDD.
     *
     * @param pageRequest les critères de recherche et de pagination
     * @return la liste des classements de l'invocateur
     */
    PageResult<Match> findMatches(PageRequest<MatchSearchCriteria> pageRequest);

    /**
     * Identifie les matchs déjà récupérés par l'invocateur.
     *
     * @param matchIds la liste des ids des matchs à vérifier
     * @param puuid le puuid de l'invocateur
     * @return la liste des ids des matchs déjà récupérés par l'invocateur
     */
    Set<String> findExistingMatchIdsByOwner(List<String> matchIds, String puuid);

    /**
     * Met à jour le statut de récupération d'un match pour un invocateur.
     * Utile dans le cas où un match a déjà été récupéré par un autre invocateur.
     *
     * @param matchId l'id du match à mettre à jour
     * @param puuid le puuid de l'invocateur
     * @return le match mis à jour
     */
    Optional<Match> addOwnerIfMatchExists(String matchId, String puuid);

    /**
     * Sauvegarde un nouveau match en BDD.
     *
     * @param match match à sauvegarder
     */
    void saveMatch(Match match);
}
