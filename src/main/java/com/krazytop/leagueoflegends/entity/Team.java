package com.krazytop.leagueoflegends.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

    private Objectives objectives;
    @JsonAlias("win")
    private Boolean hasWin;
    @JsonAlias("teamId")
    private String id;
    private List<Participant> participants;
    private List<String> bans;
    private Integer placement;

    @JsonProperty("bans")
    private void unpackBans(List<JsonNode> nodes) {
        this.bans = nodes.stream().map(node -> node.get("championId").asText()).toList();
    }

}
