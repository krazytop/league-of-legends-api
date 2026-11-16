package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;

import java.time.Instant;

@Data
public class MasterySubdocument {

    private int level;
    private int points;
    private String champion;
    private Instant lastPlayTime;
}
