package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.BoardDocument;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BoardMongoMapper {

    Board toModel(BoardDocument document);

    BoardDocument toDocument(Board board);
}
