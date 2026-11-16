package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class RankInformations {

    private Instant date;
    private String tier;
    private String rank;
    private Integer leaguePoints;
    private Integer wins;
    private Integer losses;
}
