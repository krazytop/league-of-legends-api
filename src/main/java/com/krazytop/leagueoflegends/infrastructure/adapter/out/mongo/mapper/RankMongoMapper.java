package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.SeasonRanks;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.RanksDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.SeasonRanksSubdocument;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RankMongoMapper {

    RanksDocument toDocument(String puuid, List<SeasonRanks> seasonOrSetRanks);

    List<SeasonRanks> toModels(List<SeasonRanksSubdocument> documents);
}
