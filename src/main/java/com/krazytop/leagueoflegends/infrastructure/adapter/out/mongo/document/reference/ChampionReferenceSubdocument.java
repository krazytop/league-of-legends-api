package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChampionReferenceSubdocument extends ReferenceSubdocument {

    private String title;
    private Map<String, Float> stats = new HashMap<>();
    private List<String> tags = new ArrayList<>();
}
