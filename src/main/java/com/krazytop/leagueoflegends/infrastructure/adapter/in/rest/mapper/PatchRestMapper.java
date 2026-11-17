package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper;

import com.krazytop.leagueoflegends.domain.model.reference.Patch;
import com.krazytop.leagueoflegends.domain.model.reference.RunePerkReference;
import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import com.krazytop.leagueoflegends.model.generated.RunePerkReferenceDTO;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PatchRestMapper {

    PatchDTO toDTO(Patch model);

    List<RunePerkReferenceDTO> toDTOList(List<RunePerkReference> perkList);

    List<List<RunePerkReferenceDTO>> toDTOListList(List<List<RunePerkReference>> perkList);
}
