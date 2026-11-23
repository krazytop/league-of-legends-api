package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.common.QueueMappingHelper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.MatchDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, imports = QueueMappingHelper.class)
public interface MatchMongoMapper extends PageMongoMapper<MatchDocument, Match> {

    @Mapping(target = "queue", expression = "java(QueueMappingHelper.toQueue(document.getQueue()))")
    @Mapping(target = "queue.id", source = "queue")
    Match toModel(MatchDocument document);

    @Mapping(target = "queue", source = "queue.id")
    MatchDocument toDocument(Match match);
}
