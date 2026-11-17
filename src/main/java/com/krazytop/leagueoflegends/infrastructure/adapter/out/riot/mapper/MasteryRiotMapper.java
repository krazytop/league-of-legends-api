package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import com.krazytop.leagueoflegends.domain.model.Mastery;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = DateRiotMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MasteryRiotMapper {

    @Mapping(target = "level", source = "championLevel")
    @Mapping(target = "points", source = "championPoints")
    @Mapping(target = "champion", source = "championId")
    Mastery toModel(ChampionMastery source);
}
