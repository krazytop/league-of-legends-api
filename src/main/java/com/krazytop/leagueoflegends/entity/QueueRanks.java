package com.krazytop.leagueoflegends.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueueRanks {

    private String name;
    private List<RankInformations> rankInformations = new ArrayList<>();

    public QueueRanks(String name) {
        this.name = name;
    }

    public void joinRanks(List<RankInformations> newRanks) {
        newRanks.forEach(newRank -> {
            if (rankInformations.stream().noneMatch(rank -> rank.getLosses() + rank.getWins() == newRank.getLosses() + newRank.getWins())) {
                rankInformations.add(newRank);
            }
        });
    }

}