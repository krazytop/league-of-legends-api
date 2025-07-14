package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.PatchApi;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.model.generated.PatchDTO;
import com.krazytop.leagueoflegends.service.PatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class PatchController implements PatchApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatchController.class);

    private final PatchService patchService;

    @Autowired
    public PatchController(PatchService patchService){
        this.patchService = patchService;
    }

    @Override
    public ResponseEntity<String> updateAllPatches() {
        try {
            LOGGER.info("Updating all patches");
            patchService.updateAllPatches();
            return new ResponseEntity<>("All patches are up to date", HttpStatus.OK);
        } catch (IOException | URISyntaxException ex) {
            throw new CustomException(ApiErrorEnum.PATCH_UPDATE_ERROR, ex);
        }
    }

    @Override
    public ResponseEntity<PatchDTO> getPatch(String patchId, String language) {
        LOGGER.info("Retrieving patch {} with {} language", patchId,  language);
        PatchDTO patch = patchService.getPatchDTO(patchId, language);
        LOGGER.info("Patch retrieved");
        return new ResponseEntity<>(patch, HttpStatus.OK);
    }
}