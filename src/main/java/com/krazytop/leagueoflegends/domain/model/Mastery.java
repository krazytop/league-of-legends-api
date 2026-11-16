package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Mastery {

    private int level;
    private int points;
    private String champion;
    private Instant lastPlayTime;
}
