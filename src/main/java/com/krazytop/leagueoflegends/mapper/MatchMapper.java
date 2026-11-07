package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.entity.MatchSearchCriteria;
import com.krazytop.leagueoflegends.entity.PageableSearchCriteria;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchRequestDTO;
import com.krazytop.leagueoflegends.model.generated.MatchSearchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface MatchMapper {

    MatchDTO toDTO(Match board);

    PageableSearchCriteria<MatchSearchCriteria> toModel(MatchSearchRequestDTO dto);

    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    MatchSearchResponseDTO toDTO(Page<Match> model);
}
