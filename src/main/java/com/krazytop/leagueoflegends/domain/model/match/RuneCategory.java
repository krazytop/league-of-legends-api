package com.krazytop.leagueoflegends.domain.model.match;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RuneCategory {

    private String style;
    private List<String> perks = new ArrayList<>();
}
