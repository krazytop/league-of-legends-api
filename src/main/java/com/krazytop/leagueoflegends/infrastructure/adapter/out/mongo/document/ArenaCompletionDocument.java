package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "ArenaCompletion")
public class ArenaCompletionDocument {

    @Id
    private String puuid;
    private Set<String> champions = new HashSet<>();
}
