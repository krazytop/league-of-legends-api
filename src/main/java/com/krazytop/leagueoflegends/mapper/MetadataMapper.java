package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Metadata;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MetadataMapper {

    @Mapping(source = "currentSeason", target = "currentSeasonOrSet")
    MetadataDTO toDTO(Metadata metadata);
}
