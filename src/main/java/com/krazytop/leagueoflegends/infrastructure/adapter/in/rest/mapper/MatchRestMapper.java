package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.model.generated.MatchPageDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MatchRestMapper {

    PageRequest<MatchSearchCriteria> toModel(MatchSearchRequestDTO request);

    MatchPageDTO toDTO(PageResult<Match> pageResult);

    @Mapping(target = "queue", source = "queue.id")
    MatchDTO toDTO(Match match);
}