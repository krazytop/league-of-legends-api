package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeasonOrSetRanks {

    private Integer seasonOrSetNumber;
    private List<QueueRanks> queueRanks = new ArrayList<>();
}
