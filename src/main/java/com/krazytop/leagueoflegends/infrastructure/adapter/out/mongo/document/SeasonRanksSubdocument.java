package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeasonRanksSubdocument {

    private int season;
    private List<QueueRanksSubdocument> queueRanks = new ArrayList<>();
}
