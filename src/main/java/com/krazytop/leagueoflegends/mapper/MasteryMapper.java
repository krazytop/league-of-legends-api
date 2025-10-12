package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Masteries;
import com.krazytop.leagueoflegends.model.generated.MasteriesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface MasteryMapper {

    MasteriesDTO toDTO(Masteries model);
}
