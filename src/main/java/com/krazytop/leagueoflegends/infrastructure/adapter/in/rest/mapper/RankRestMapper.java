package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.SeasonOrSetRanks;
import com.krazytop.leagueoflegends.model.generated.SeasonOrSetRanksDTO;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RankRestMapper {

    SeasonOrSetRanksDTO toDTO(SeasonOrSetRanks model);
}
