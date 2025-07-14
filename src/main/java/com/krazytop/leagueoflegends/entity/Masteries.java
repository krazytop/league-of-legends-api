package com.krazytop.leagueoflegends.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "Mastery")
public class Masteries {

    @Id private String puuid;
    private List<Mastery> champions = new ArrayList<>();

    public Masteries(String puuid){
        this.puuid = puuid;
    }
}

