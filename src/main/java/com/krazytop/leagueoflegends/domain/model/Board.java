package com.krazytop.leagueoflegends.domain.model;

import com.krazytop.leagueoflegends.domain.exception.SummonerAlreadyInBoardException;
import com.krazytop.leagueoflegends.domain.exception.TooManySummonersException;
import lombok.Data;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class Board {

    private static final int MAX_SUMMONERS = 10;

    private String id;
    private Set<String> puuids = new LinkedHashSet<>();
    private Instant updateDate;
    private Instant creationDate;
    private String name;

    /**
     * Ajoute un invocateur au board.
     *
     * @param puuid le puuid de l'invocateur
     */
    public void addSummoner(String puuid) {
        if (puuids.contains(puuid)) throw new SummonerAlreadyInBoardException(id, puuid);
        if (puuids.size() >= MAX_SUMMONERS) throw new TooManySummonersException(id, puuid);
        puuids.add(puuid);
    }

    /**
     * Supprime un invocateur du board.
     *
     * @param puuid le puuid de l'invocateur
     */
    public void removeSummoner(String puuid) {
        puuids.remove(puuid);
    }
}
