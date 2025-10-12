package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.model.generated.PageableRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.PageRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PageableMapper {

    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "pageNumber", source = "page")
    PageRequest toModel(PageableRequestDTO dto);
}
