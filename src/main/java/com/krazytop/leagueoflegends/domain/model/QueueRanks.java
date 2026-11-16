package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueueRanks {

    private String name;
    private List<RankInformations> rankInformations = new ArrayList<>();
}
