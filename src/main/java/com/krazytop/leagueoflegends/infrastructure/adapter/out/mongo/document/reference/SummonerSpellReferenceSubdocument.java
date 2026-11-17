package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SummonerSpellReferenceSubdocument extends ReferenceSubdocument {

    private String tooltip;
    private Float cooldownBurn;
}
