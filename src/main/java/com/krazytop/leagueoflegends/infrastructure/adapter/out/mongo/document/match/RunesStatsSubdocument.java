package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunesStatsSubdocument {

    private String defense;
    private String flex;
    private String offense;
}