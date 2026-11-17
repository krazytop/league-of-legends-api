package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SummonerSpellReference extends Reference {

    private String tooltip;
    private Float cooldownBurn;
}
