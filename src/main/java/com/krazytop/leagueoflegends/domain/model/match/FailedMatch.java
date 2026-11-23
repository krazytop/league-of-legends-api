package com.krazytop.leagueoflegends.domain.model.match;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class FailedMatch {

    private String id;
    private EnumRegion region;
    private Set<String> owners = new HashSet<>();
    private String errorType;
    private String errorMessage;
    private int retryCount;
    private Instant createdAt;
    private Instant lastAttemptAt;
}