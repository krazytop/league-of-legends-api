package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Board;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface BoardMapper {

    BoardDTO toDTO(Board model);
}
