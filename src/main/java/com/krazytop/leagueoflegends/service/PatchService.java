package com.krazytop.leagueoflegends.service;

import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.PatchMapper;
import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import com.krazytop.leagueoflegends.nomenclature.*;
import com.krazytop.leagueoflegends.repository.PatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PatchService {

    private final PatchRepository patchNomenclatureRepository;
    private final PatchMapper patchMapper;

    public Optional<Patch> getPatch(String patchId, String language) {
        return patchNomenclatureRepository.findFirstByPatchIdAndLanguage(patchId, language);
    }

    public PatchDTO getPatchDTO(String patchId, String language) {
        return patchMapper.toDTO(getPatch(patchId, language).orElseThrow(() -> new CustomException(ApiErrorEnum.PATCH_NOT_FOUND)));
    }

}