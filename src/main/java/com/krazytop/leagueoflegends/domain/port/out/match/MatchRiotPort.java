package com.krazytop.leagueoflegends.domain.port.out.match;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.Match;

import java.util.List;

public interface MatchRiotPort {

    /**
     * Récupère les ids des matchs d'un invocateur sur la plage définie.
     *
     * @param region la région de l'invocateur
     * @param puuid le puuid de l'invocateur
     * @param start l'index de début des ids des matchs remontés
     * @param count le nombre d'ids à remonter
     * @return les ids des matchs de l'invocateur
     */
    List<String> getMatchIds(EnumRegion region, String puuid, int start, int count);

    /**
     * Récupère un match depuis l'api de Riot.
     *
     * @param region la région de l'invocateur
     * @param matchId le puuid de l'invocateur
     * @return le match
     */
    Match getMatch(EnumRegion region, String matchId);
}