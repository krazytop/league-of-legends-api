package com.krazytop.leagueoflegends.domain.model.searchcriteria;

import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRole;
import lombok.Data;

@Data
public class MatchSearchCriteria {

    private String puuid;
    private EnumQueue queue;
    private EnumRole role;
    private Integer season;
}