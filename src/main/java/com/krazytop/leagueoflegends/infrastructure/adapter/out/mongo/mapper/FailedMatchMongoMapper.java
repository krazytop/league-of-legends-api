package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.match.FailedMatch;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.FailedMatchDocument;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FailedMatchMongoMapper extends PageMongoMapper<FailedMatchDocument, FailedMatch> {

    FailedMatch toModel(FailedMatchDocument document);

    FailedMatchDocument toDocument(FailedMatch match);
}
