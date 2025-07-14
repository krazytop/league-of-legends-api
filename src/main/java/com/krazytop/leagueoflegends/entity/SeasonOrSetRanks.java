package com.krazytop.leagueoflegends.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class SeasonOrSetRanks {

    private Integer nb;
    private List<QueueRanks> queueRanks = new ArrayList<>();

    public SeasonOrSetRanks(int nb) {
        this.nb = nb;
    }

    public void joinRanks(List<RankInformations> newRanksInformations, String queueName) {
        QueueRanks queueRank = queueRanks.stream()
                .filter(q -> Objects.equals(q.getName(), queueName))
                .findFirst()
                .orElseGet(() -> {
                    QueueRanks newQueueRank = new QueueRanks(queueName);
                    queueRanks.add(newQueueRank);
                    return newQueueRank;
                });
        queueRank.joinRanks(newRanksInformations);
    }

}