package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RuneCategorySubdocument {

    private String style;
    private List<String> perks = new ArrayList<>();
}
