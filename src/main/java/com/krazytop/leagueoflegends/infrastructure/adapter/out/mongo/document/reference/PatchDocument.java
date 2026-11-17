package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Patch")
public class PatchDocument {

    private String id;
    private String patchId;
    private String language;
    private Integer season;
    private List<ChampionReferenceSubdocument> champions = new ArrayList<>();
    private List<ItemReferenceSubdocument> items = new ArrayList<>();
    private List<SummonerSpellReferenceSubdocument> summonerSpells = new ArrayList<>();
    private List<AugmentReferenceSubdocument> augments = new ArrayList<>();
    private List<RuneReferenceSubdocument> runes = new ArrayList<>();
    private List<QueueReferenceSubdocument> queues = new ArrayList<>();
}
