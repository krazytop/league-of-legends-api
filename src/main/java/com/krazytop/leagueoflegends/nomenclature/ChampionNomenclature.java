package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChampionNomenclature extends Nomenclature {

    private String title;
    private Map<String, Float> stats;
    private List<String> tags;
}
