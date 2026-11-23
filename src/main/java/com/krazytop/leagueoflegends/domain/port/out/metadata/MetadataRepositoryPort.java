package com.krazytop.leagueoflegends.domain.port.out.metadata;

import com.krazytop.leagueoflegends.domain.model.Metadata;

public interface MetadataRepositoryPort {

    /**
     * Récupère les métadonnées actuelles de League of Legends nécessaire au bon fonctionnement de l'application.
     *
     * @return les métadonnées actuelles
     */
    Metadata findMetadata();
}
