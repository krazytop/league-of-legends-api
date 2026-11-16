package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeasonOrSetRanksSubdocument {

    private Integer seasonOrSetNumber;
    private List<QueueRanksSubdocument> queueRanks = new ArrayList<>();
}
