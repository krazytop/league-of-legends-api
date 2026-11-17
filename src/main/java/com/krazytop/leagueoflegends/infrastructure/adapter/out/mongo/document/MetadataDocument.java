package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "Metadata")
public class MetadataDocument {

    @Id
    private Integer id = 1;
    private Integer currentSeason;
    private String currentPatch;
    private Set<String> allPatches = new HashSet<>();
}
