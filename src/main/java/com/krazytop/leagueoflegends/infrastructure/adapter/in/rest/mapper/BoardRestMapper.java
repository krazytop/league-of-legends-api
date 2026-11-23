package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.domain.model.BoardUpdateRequest;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import com.krazytop.leagueoflegends.model.generated.BoardUpdateRequestDTO;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BoardRestMapper {

    BoardDTO toDTO(Board model);

    BoardUpdateRequest toModel(BoardUpdateRequestDTO dto);
}
