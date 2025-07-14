package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.ArenaCompletion;
import com.krazytop.leagueoflegends.model.generated.ArenaCompletionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArenaCompletionMapper {

    ArenaCompletionDTO toDTO(ArenaCompletion arenaCompletion);
}
