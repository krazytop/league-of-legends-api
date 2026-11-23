package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.Metadata;
import com.krazytop.leagueoflegends.domain.port.out.metadata.MetadataRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MetadataService {

    private final MetadataRepositoryPort repositoryPort;

    /**
     * Récupère les métadonnées actuelles de League of Legends nécessaire au bon fonctionnement de l'application.
     *
     * @return les métadonnées actuelles
     */
    public Metadata getMetadata() {
        return repositoryPort.findMetadata();
    }

    /**
     * Récupère le numéro de la saison actuelle.
     *
     * @return le numéro de la saison actuelle
     */
    public int getCurrentSeason() {
        return getMetadata().getCurrentSeason();
    }
}
