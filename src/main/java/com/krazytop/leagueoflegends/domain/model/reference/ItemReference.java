package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemReference extends Reference {

    private String plainText;
    private int baseGold;
    private int totalGold;
    private List<String> tags;
    private Map<String, Float> stats = new HashMap<>();
    private List<String> toItems = new ArrayList<>();
    private List<String> fromItems = new ArrayList<>();
}
