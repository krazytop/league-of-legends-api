package com.krazytop.leagueoflegends.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuneCategory {

    private String style;
    private List<String> perks;

    @JsonProperty("selections")
    private void unpackStyles(List<JsonNode> nodes) {
        this.perks = nodes.stream().map(node -> node.get("perk").asText()).toList();
    }
}
