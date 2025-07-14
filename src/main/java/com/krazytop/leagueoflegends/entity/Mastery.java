package com.krazytop.leagueoflegends.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mastery {

    @JsonAlias("championLevel")
    private int level;
    @JsonAlias("championPoints")
    private int points;
    @JsonAlias("championId")
    private String champion;
    private Date lastPlayTime;

    @JsonProperty("lastPlayTime")
    private void unpackLastPlayTime(JsonNode node) {
        this.lastPlayTime = new Date(node.asLong());
    }
}
