package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.PatchApi;
import com.krazytop.leagueoflegends.domain.service.PatchService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.PatchRestMapper;
import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class PatchController implements PatchApi {

    private final PatchService service;
    private final PatchRestMapper mapper;

    @Override
    public ResponseEntity<PatchDTO> getPatch(String patchId, String language) {
        log.info("Récupération du patch {} en langue {}", patchId,  language);
        PatchDTO patch = mapper.toDTO(
                service.getPatch(patchId, language));
        return ResponseEntity.ok(patch);
    }
}