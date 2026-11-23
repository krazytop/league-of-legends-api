package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.Metadata;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MetadataRestMapper {

    @Mapping(target = "currentSeason", source = "currentSeason")
    MetadataDTO toDTO(Metadata model);
}
