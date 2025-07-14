package com.krazytop.leagueoflegends.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Document(collection = "Rank")
public class Rank {

    @Id
    private String puuid;
    private List<SeasonOrSetRanks> seasonOrSetRanks = new ArrayList<>();

    public Rank(String puuid) {
        this.puuid = puuid;
    }

    public void joinRanks(List<RankInformations> newRanksInformations, int seasonOrSetNb, String queueName) {
        SeasonOrSetRanks seasonOrSetRank = seasonOrSetRanks.stream()
                .filter(s -> Objects.equals(s.getNb(), seasonOrSetNb))
                .findFirst()
                .orElseGet(() -> {
                    SeasonOrSetRanks newSeasonOrSetRank = new SeasonOrSetRanks(seasonOrSetNb);
                    seasonOrSetRanks.add(newSeasonOrSetRank);
                    return newSeasonOrSetRank;
                });
        seasonOrSetRank.joinRanks(newRanksInformations, queueName);
    }

}