package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class Summoner {

    private String puuid;
    private String name;
    private int level;
    private int icon;
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
}
