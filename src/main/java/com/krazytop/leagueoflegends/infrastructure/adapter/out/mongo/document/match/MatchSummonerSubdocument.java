package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchSummonerSubdocument {

    @Id
    private String puuid;
    private String name;
    private int level;
    private String icon;
    private String tag;
}
