package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Patch")
public class Patch {

    private String id;
    private String patchId;
    private String language;
    private Integer season;
    private List<ChampionNomenclature> champions;
    private List<ItemNomenclature> items;
    private List<SummonerSpellNomenclature> summonerSpells;
    private List<AugmentNomenclature> augments;
    private List<RuneNomenclature> runes;
    private List<QueueNomenclature> queues;
}
