package com.krazytop.leagueoflegends.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@Document(collection = "ArenaCompletion")
public class ArenaCompletion {

    @Id
    private String puuid;
    private Set<String> champions = new HashSet<>();
}
