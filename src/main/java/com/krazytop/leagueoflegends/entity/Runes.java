package com.krazytop.leagueoflegends.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Runes {

    @JsonAlias("statPerks")
    private Map<String, Float> stats;
    @JsonAlias("styles")
    private List<RuneCategory> runeCategories;
}
