package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import com.krazytop.leagueoflegends.domain.model.EnumGlobalRegion;
import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShardRiotMapper {

    default LeagueShard toDTO(EnumRegion region) {
        return switch (region) {
            case BR1 -> LeagueShard.BR1;
            case EUN1 -> LeagueShard.EUN1;
            case EUW1 -> LeagueShard.EUW1;
            case JP1 -> LeagueShard.JP1;
            case KR -> LeagueShard.KR;
            case LA1 -> LeagueShard.LA1;
            case LA2 -> LeagueShard.LA2;
            case NA1 -> LeagueShard.NA1;
            case OC1 -> LeagueShard.OC1;
            case TR1 -> LeagueShard.TR1;
            case RU -> LeagueShard.RU;
            case PBE1 -> LeagueShard.PBE1;
            case SG2 -> LeagueShard.SG2;
            case PH2 -> LeagueShard.PH2;
            case ID1 -> LeagueShard.ID1;
            case VN2 -> LeagueShard.VN2;
            case TH2 -> LeagueShard.TH2;
            case TW2 -> LeagueShard.TW2;
            case ME1 -> LeagueShard.ME1;
        };
    }

    default RegionShard toDTO(EnumGlobalRegion region) {
        return switch (region) {
            case AMERICAS -> RegionShard.AMERICAS;
            case EUROPE -> RegionShard.EUROPE;
            case ASIA -> RegionShard.ASIA;
            case SEA -> RegionShard.SEA;
        };
    }
}
