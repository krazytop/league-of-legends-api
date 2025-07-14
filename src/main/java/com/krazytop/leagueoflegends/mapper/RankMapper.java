package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Rank;
import com.krazytop.leagueoflegends.model.generated.RankDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface RankMapper {

    RankDTO toDTO(Rank rank);
}
