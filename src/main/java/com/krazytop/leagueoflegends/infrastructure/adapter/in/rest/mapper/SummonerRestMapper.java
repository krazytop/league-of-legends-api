package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SummonerRestMapper {

    @Mapping(target = "playedSeasonsOrSets", source = "playedSeasons")
    SummonerDTO toDTO(Summoner summoner);
}
