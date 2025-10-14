package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SummonerSpellNomenclature extends Nomenclature {

    private String tooltip;
    private Float cooldownBurn;
}
