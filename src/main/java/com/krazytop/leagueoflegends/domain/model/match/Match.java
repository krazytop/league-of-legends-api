package com.krazytop.leagueoflegends.domain.model.match;

import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import lombok.Data;

import java.time.Instant;
import java.util.*;

@Data
public class Match {

    private String id;
    private String version;
    private int season;
    private Instant datetime;
    private Long duration;
    private List<Team> teams = new ArrayList<>();
    private boolean remake;
    private Queue queue;
    private Set<String> owners = new HashSet<>();

    /**
     * Récupère le champion joué par l'invocateur lors d'une victoire en Arena
     *
     * @param puuid le puuid de l'invocateur
     * @return le champion joué si le match est gagné en Arena
     */
    public Optional<String> getArenaWonChampionFor(String puuid) {
        if (queue == null || queue.getType() != EnumQueue.ARENA) {
            return Optional.empty();
        }
        return teams.stream()
                .filter(team -> team.getPlacement() == 1)
                .flatMap(team -> team.getParticipants().stream())
                .filter(participant -> puuid.equals(participant.getSummoner().getPuuid()))
                .map(Participant::getChampion)
                .findFirst();
    }
}