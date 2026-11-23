package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.SeasonRanks;
import com.krazytop.leagueoflegends.model.generated.SeasonRanksDTO;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RankRestMapper {

    SeasonRanksDTO toDTO(SeasonRanks model);
}
