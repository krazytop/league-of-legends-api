package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.model.MatchFilters;
import com.krazytop.leagueoflegends.model.PageFilteredRequest;
import com.krazytop.leagueoflegends.model.generated.MatchDTO;
import com.krazytop.leagueoflegends.model.generated.PageableRequestMatchDTO;
import com.krazytop.leagueoflegends.model.generated.PageableResponseMatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = DateMapper.class)
public interface MatchMapper {

    MatchDTO toDTO(Match model);

    @Mapping(target = "page", source = "number")
    PageableResponseMatchDTO toDTO(Page<Match> models);

    PageFilteredRequest<MatchFilters> toModel(PageableRequestMatchDTO model);
}
