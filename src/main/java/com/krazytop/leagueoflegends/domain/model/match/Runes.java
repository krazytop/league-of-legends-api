package com.krazytop.leagueoflegends.domain.model.match;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Runes {

    private RunesStats stats;
    private List<RuneCategory> runeCategories = new ArrayList<>();
}
