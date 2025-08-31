package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.PatchApi;
import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import com.krazytop.leagueoflegends.service.PatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PatchController implements PatchApi {

    private final PatchService patchService;

    @Autowired
    public PatchController(PatchService patchService){
        this.patchService = patchService;
    }

    @Override
    public ResponseEntity<PatchDTO> getPatch(String patchId, String language) {
        log.info("Retrieving patch {} with {} language", patchId,  language);
        PatchDTO patch = patchService.getPatchDTO(patchId, language);
        log.info("Patch retrieved");
        return new ResponseEntity<>(patch, HttpStatus.OK);
    }
}