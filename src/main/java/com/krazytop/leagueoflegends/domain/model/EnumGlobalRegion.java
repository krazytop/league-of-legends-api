package com.krazytop.leagueoflegends.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumGlobalRegion {//TODO toRegionShard dans les adapters

    /**
     * Americas platform.
     */
    AMERICAS("AMERICAS"),
    /**
     * Europe platform.
     */
    EUROPE("EUROPE"),
    /**
     * Asia platform.
     */
    ASIA("ASIA"),
    /**
     * South-East Asia platform.
     */
    SEA("SEA");

    private final String code;
}
