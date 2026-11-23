package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.*;

@Data
@Document(collection = "Match")
public class MatchDocument {

    private String id;
    private String version;
    private int season;
    private Instant datetime;
    private Long duration;
    private List<TeamSubdocument> teams = new ArrayList<>();
    private Boolean remake;
    private String queue;
    @Indexed
    private Set<String> owners = new HashSet<>();
}