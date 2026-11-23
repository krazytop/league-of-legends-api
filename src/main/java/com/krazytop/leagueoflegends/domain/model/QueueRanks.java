package com.krazytop.leagueoflegends.domain.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueueRanks {

    private String name;
    private List<RankInformations> rankInformations = new ArrayList<>();

    /**
     * Fusionne les classements récupérés, à jour, avec l'historique existant.
     * La fusion ignore les doublons en se basant sur le nombre de matchs joués.
     *
     * @param newInfos la liste des nouvelles informations de classement à fusionner
     */
    public void mergeRankInformations(List<RankInformations> newInfos) {
        List<RankInformations> updatedInfos = new ArrayList<>(rankInformations);

        newInfos.stream()
                .filter(newInfo -> updatedInfos.isEmpty()
                        || !updatedInfos.getLast().equalsOnMatchesCount(newInfo))
                .forEach(updatedInfos::add);

        rankInformations = updatedInfos;
    }
}
