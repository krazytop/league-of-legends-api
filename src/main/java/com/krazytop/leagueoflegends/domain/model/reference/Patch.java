package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Patch {

    private String id;
    private String patchId;
    private String language;
    private Integer season;
    private List<ChampionReference> champions = new ArrayList<>();
    private List<ItemReference> items = new ArrayList<>();
    private List<SummonerSpellReference> summonerSpells = new ArrayList<>();
    private List<AugmentReference> augments = new ArrayList<>();
    private List<RuneReference> runes = new ArrayList<>();
    private List<QueueReference> queues = new ArrayList<>();
}
