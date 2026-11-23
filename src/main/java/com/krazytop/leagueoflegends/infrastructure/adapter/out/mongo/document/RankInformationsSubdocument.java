package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;

import java.time.Instant;

@Data
public class RankInformationsSubdocument {

    private Instant date;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
}
