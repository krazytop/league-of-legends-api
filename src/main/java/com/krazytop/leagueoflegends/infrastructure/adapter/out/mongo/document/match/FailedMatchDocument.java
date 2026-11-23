package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "FailedMatch")
public class FailedMatchDocument {

    @Id
    private String id;
    private EnumRegion region;
    @Indexed
    private Set<String> owners = new HashSet<>();
    private String errorType;
    private String errorMessage;
    private int retryCount;
    private Instant createdAt;
    private Instant lastAttemptAt;

}