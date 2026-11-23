package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.Data;

@Data
public class ParticipantSubdocument {

    private Integer champLevel;
    private Integer kills;
    private Integer assists;
    private Integer deaths;
    private String role;
    private Integer visionScore;
    private Integer minions;
    private Integer neutralMinions;
    private Integer doubleKills;
    private Integer tripleKills;
    private Integer quadraKills;
    private Integer pentaKills;
    private Integer physicalDamageDealtToChampions;
    private Integer magicDamageDealtToChampions;
    private Integer trueDamageDealtToChampions;
    private Integer physicalDamageTaken;
    private Integer magicDamageTaken;
    private Integer trueDamageTaken;
    private Integer golds;
    private Boolean gameEndedInEarlySurrender;
    private RunesSubdocument runes;
    private String champion;
    private String item0;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String ward;
    private MatchSummonerSubdocument summoner;
    private String summonerSpell1;
    private String summonerSpell2;
    private String augment1;
    private String augment2;
    private String augment3;
    private String augment4;
    private String augment5;
    private String augment6;
}
