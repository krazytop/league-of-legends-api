package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RunesSubdocument {

    private RunesStatsSubdocument stats;
    private List<RuneCategorySubdocument> runeCategories = new ArrayList<>();
}
