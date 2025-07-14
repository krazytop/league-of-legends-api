package com.krazytop.leagueoflegends.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Runes {

    @JsonAlias("statPerks")
    private Map<String, Integer> stats;
    @JsonAlias("styles")
    private List<RuneCategory> runeCategories;
}
