package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Summoner")
public class SummonerDocument {

    @Id
    private String puuid;
    private String name;
    private Integer level;
    private Integer icon;
    private String tag;
    private String region;
    private Instant updateDate;
    private long spentTime;
    private Set<Integer> playedSeasons = new HashSet<>();
}
