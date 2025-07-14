package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface MatchMapper {

    MatchDTO toDTO(Match board);
}
