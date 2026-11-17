package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import com.krazytop.leagueoflegends.domain.model.Summoner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SummonerRiotMapper {

    @Mapping(target = "puuid", source = "PUUID")
    @Mapping(target = "level", source = "summonerLevel")
    @Mapping(target = "icon", source = "profileIconId")
    Summoner toModel(no.stelar7.api.r4j.pojo.lol.summoner.Summoner summoner);
}
