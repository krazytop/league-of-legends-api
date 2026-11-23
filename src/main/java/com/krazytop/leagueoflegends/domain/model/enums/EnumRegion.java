package com.krazytop.leagueoflegends.domain.model.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum EnumRegion {

    /**
     * BRAZIL platform.
     */
    BR1("BR1"),
    /**
     * Europe Nordic &amp; East platform.
     */
    EUN1("EUN1"),
    /**
     * Europe West platform.
     */
    EUW1("EUW1"),
    /**
     * Japan platform.
     */
    JP1("JP1"),
    /**
     * Korea platform.
     */
    KR("KR"),
    /**
     * Latin America North platform.
     */
    LA1("LA1"),
    /**
     * Latin America South platform.
     */
    LA2("LA2"),
    /**
     * North America platform.
     */
    NA1("NA1"),
    /**
     * Oceania platform.
     */
    OC1("OC1"),
    /**
     * Turkey platform.
     */
    TR1("TR1"),
    /**
     * Russia platform.
     */
    RU("RU"),
    /**
     * Public Beta Environment platform.
     */
    PBE1("PBE1"),
    /**
     * Singapore platform
     */
    SG2("SG2"),
    /**
     * Philippines platform
     */
    PH2("PH2"),
    /**
     * Indonesia platform
     */
    ID1("ID1"),
    /**
     * Vietnam platform
     */
    VN2("VN2"),
    /**
     * Thailand platform
     */
    TH2("TH2"),
    /**
     * Taiwan platform
     */
    TW2("TW2"),
    /**
     * Middle East platform
     */
    ME1("ME1");

    private final String code;

    public static EnumRegion fromCode(String code) {
        return Arrays.stream(values())
                .filter(region -> region.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Code de région inconnu : " + code));
    }
}
