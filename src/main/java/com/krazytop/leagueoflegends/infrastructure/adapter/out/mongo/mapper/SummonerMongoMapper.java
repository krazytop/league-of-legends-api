package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.SummonerDocument;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SummonerMongoMapper {

    Summoner toModel(SummonerDocument document);

    SummonerDocument toDocument(Summoner model);
}
