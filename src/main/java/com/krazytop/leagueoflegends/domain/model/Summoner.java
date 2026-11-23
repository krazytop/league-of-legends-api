package com.krazytop.leagueoflegends.domain.model;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class Summoner {

    private String puuid;
    private String name;
    private int level;
    private String icon;
    private String tag;
    private EnumRegion region;
    private Instant updateDate;
    private long spentTime;
    private Set<Integer> playedSeasons = new HashSet<>();

    /**
     * Met à jour le modèle avec les informations à jour du compte de l'invocateur
     *
     * @param account le compte de l'invocateur
     * @return l'invocateur mis à jour
     */
    public Summoner updateWith(Account account) {
        return this.setName(account.getName())
                .setTag(account.getTag())
                .setRegion(account.getRegion());
    }

    /**
     * Met à jour le modèle après un nouveau match joué par l'invocateur
     *
     * @param match le nouveau match joué
     */
    public void updateWith(Match match) {
        this.spentTime += match.getDuration();
        this.playedSeasons.add(match.getSeason());
    }
}
