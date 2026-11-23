package com.krazytop.leagueoflegends.domain.model;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import lombok.Data;

@Data
public class Account {

    private String puuid;
    private String name;
    private String tag;
    private EnumRegion region;
}
