package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.model.reference.Patch;
import com.krazytop.leagueoflegends.domain.port.out.patch.PatchRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatchService {

    private final PatchRepositoryPort repositoryPort;

    /**
     * Récupère le patch pour une version et une langue précises.
     *
     * @param patchId l'id du patch
     * @param language la langue voulue
     * @return toutes les informations du patch
     */
    public Patch getPatch(String patchId, String language) {
        return repositoryPort.findPatch(patchId, language)
                .orElseThrow(() -> new ResourceNotFoundException("Le patch %s en langue %s n'existe pas ou n'est pas encore disponible".formatted(patchId, language)));
    }
}
