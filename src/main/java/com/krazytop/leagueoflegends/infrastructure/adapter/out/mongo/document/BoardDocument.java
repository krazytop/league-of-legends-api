package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Document(collection = "Board")
public class BoardDocument {

    @Id
    private String id;
    private Set<String> puuids = new LinkedHashSet<>();
    private Instant updateDate;
    private Instant creationDate;
    private String name;
}
