package com.krazytop.leagueoflegends.domain.model;

import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.match.Participant;
import com.krazytop.leagueoflegends.domain.model.match.Queue;
import com.krazytop.leagueoflegends.domain.model.match.Team;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MatchTest {

    private static final String TARGET_PUUID = "puuid-target";
    private static final String OTHER_PUUID = "puuid-other";
    private static final Queue ARENA_QUEUE = new Queue().setId("1700").setType(EnumQueue.ARENA);

    @Test
    void getArenaWonChampionFor_shouldReturnChampion_whenArenaAndPlacementIs1() {
        // Arrange
        Match match = new Match()
                .setQueue(ARENA_QUEUE)
                .setTeams(List.of(
                        createTeam(2, createParticipant(OTHER_PUUID, "Zed")),
                        createTeam(1, createParticipant(TARGET_PUUID, "Ahri"))
                ));

        // Act
        Optional<String> result = match.getArenaWonChampionFor(TARGET_PUUID);

        // Assert
        assertThat(result).contains("Ahri");
    }

    @Test
    void getArenaWonChampionFor_shouldReturnEmpty_whenQueueIsNotArena() {
        // Arrange
        Match match = new Match()
                .setQueue(new Queue().setId("420").setType(EnumQueue.SOLO_RANKED))
                .setTeams(List.of(
                        createTeam(1, createParticipant(TARGET_PUUID, "Ahri"))
                ));

        // Act
        Optional<String> result = match.getArenaWonChampionFor(TARGET_PUUID);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void getArenaWonChampionFor_shouldReturnEmpty_whenPlacementIsNot1() {
        // Arrange
        Match match = new Match()
                .setQueue(ARENA_QUEUE)
                .setTeams(List.of(
                        createTeam(2, createParticipant(TARGET_PUUID, "Ahri")),
                        createTeam(1, createParticipant(OTHER_PUUID, "LeeSin"))
                ));

        // Act
        Optional<String> result = match.getArenaWonChampionFor(TARGET_PUUID);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void getArenaWonChampionFor_shouldReturnEmpty_whenPlayerNotInWinningTeam() {
        // Arrange
        Match match = new Match()
                .setQueue(ARENA_QUEUE)
                .setTeams(List.of(
                        createTeam(1, createParticipant(OTHER_PUUID, "LeeSin"))
                ));

        // Act
        Optional<String> result = match.getArenaWonChampionFor(TARGET_PUUID);

        // Assert
        assertThat(result).isEmpty();
    }

    private Team createTeam(int placement, Participant... participants) {
        return new Team()
                .setPlacement(placement)
                .setParticipants(List.of(participants));
    }

    private Participant createParticipant(String puuid, String champion) {
        Summoner summoner = new Summoner().setPuuid(puuid);
        return new Participant()
                .setSummoner(summoner)
                .setChampion(champion);
    }
}