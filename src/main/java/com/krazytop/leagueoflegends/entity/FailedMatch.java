package com.krazytop.leagueoflegends.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "FailedMatch")
public class FailedMatch {

    private String id;
    private Set<String> owners = new HashSet<>();
}
