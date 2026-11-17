package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.model.generated.MasteryDTO;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MasteryRestMapper {

    MasteryDTO toDTO(Mastery model);
}
