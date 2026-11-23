package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class RankInformations {

    private Instant date;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    /**
     * Vérifie si des matchs ont été joué entre deux classements.
     * Utile pour savoir s'il faut mettre à jour le classement d'un invocateur.
     *
     * @param newRank le nouveau classement
     * @return s'il y a eu des nouveaux matchs de joués
     */
    public boolean equalsOnMatchesCount(RankInformations newRank) {
        return (wins + losses) == (newRank.getWins() + newRank.getLosses());
    }
}
