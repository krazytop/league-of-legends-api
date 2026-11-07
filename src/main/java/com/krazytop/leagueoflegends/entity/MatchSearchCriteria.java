package com.krazytop.leagueoflegends.entity;

import com.krazytop.leagueoflegends.nomenclature.QueueEnum;
import com.krazytop.leagueoflegends.nomenclature.RoleEnum;
import lombok.Data;

@Data
public class MatchSearchCriteria {

    private String puuid;
    private QueueEnum queue;
    private RoleEnum role;
}
