package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemNomenclature extends Nomenclature {

    private String plainText;
    private int baseGold;
    private int totalGold;
    private List<String> tags;
    private Map<String, Float> stats;
    private List<String> toItems;
    private List<String> fromItems;
}
