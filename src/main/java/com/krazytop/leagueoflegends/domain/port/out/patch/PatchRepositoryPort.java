package com.krazytop.leagueoflegends.domain.port.out.patch;

import com.krazytop.leagueoflegends.domain.model.reference.Patch;

import java.util.Optional;

public interface PatchRepositoryPort {

    /**
     * Récupère le patch pour une version et une langue précises depuis la BDD.
     *
     * @param patchId l'id du patch
     * @param language la langue voulue
     * @return toutes les informations du patch
     */
    Optional<Patch> findPatch(String patchId, String language);
}
