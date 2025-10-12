package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Summoner;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface SummonerMapper {

    SummonerDTO toDTO(Summoner model);
}
