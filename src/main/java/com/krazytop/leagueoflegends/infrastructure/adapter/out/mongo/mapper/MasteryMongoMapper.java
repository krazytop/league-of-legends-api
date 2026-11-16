package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MasteriesDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MasterySubdocument;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MasteryMongoMapper {

    MasteriesDocument toDocument(String puuid, List<Mastery> champions);

    List<Mastery> toModels(List<MasterySubdocument> documents);
}
