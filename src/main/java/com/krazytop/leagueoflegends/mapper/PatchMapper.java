package com.krazytop.leagueoflegends.mapper;

import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import com.krazytop.leagueoflegends.model.generated.RunePerkNomenclatureDTO;
import com.krazytop.leagueoflegends.nomenclature.Patch;
import com.krazytop.leagueoflegends.nomenclature.RunePerkNomenclature;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatchMapper {

    PatchDTO toDTO(Patch patch);
    List<RunePerkNomenclatureDTO> toDTOList(List<RunePerkNomenclature> perkList);
    List<List<RunePerkNomenclatureDTO>> toDTOListOfLists(List<List<RunePerkNomenclature>> perkList);
}
