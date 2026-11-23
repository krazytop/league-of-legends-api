package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.ArenaCompletionDocument;
import org.mapstruct.Mapper;

import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ArenaCompletionMongoMapper {

    ArenaCompletionDocument toDocument(String puuid, Set<String> champions);
}
