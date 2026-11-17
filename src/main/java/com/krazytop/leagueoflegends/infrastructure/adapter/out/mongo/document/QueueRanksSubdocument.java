package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueueRanksSubdocument {

    private String name;
    private List<RankInformationsSubdocument> rankInformations = new ArrayList<>();
}
