package com.krazytop.leagueoflegends.domain.model.match;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Team {

    private String id;
    private Map<String, Integer> objectives;
    private boolean hasWin;
    private List<Participant> participants = new ArrayList<>();
    private List<String> bans = new ArrayList<>();
    private Integer placement;

}
