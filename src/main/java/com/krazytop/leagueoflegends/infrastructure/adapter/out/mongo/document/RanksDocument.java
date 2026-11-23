package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Rank")
public class RanksDocument {

    @Id
    private String puuid;
    private List<SeasonRanksSubdocument> seasonOrSetRanks = new ArrayList<>();
}
