package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TeamSubdocument {

    private String id;
    private Map<String, Integer> objectives;
    private boolean hasWin;
    private List<ParticipantSubdocument> participants = new ArrayList<>();
    private List<String> bans = new ArrayList<>();
    private Integer placement;
}
