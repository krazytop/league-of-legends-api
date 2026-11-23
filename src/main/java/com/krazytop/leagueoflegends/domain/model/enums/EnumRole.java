package com.krazytop.leagueoflegends.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumRole {

    TOP("TOP"),
    JUNGLE("JUNGLE"),
    MIDDLE("MID"),
    BOTTOM("BOT"),
    SUPPORT("UTILITY");

    private final String value;
}
