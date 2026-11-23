package com.krazytop.leagueoflegends.domain.model;

import com.krazytop.leagueoflegends.domain.exception.SummonerAlreadyInBoardException;
import com.krazytop.leagueoflegends.domain.exception.TooManySummonersException;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void addSummoner() {
        // Arrange
        Board board = new Board().setId("board-123");

        // Act
        board.addSummoner("puuid-1");
        board.addSummoner("puuid-2");

        // Assert
        assertThat(board.getPuuids()).containsExactly("puuid-1", "puuid-2");
    }

    @Test
    void addSummoner_alreadyInBoard() {
        // Arrange
        Board board = new Board()
                .setId("board-123")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1234")));

        // Act & Assert
        assertThatThrownBy(() -> board.addSummoner("puuid-1234"))
                .isInstanceOf(SummonerAlreadyInBoardException.class);
        assertThat(board.getPuuids()).containsExactly("puuid-1234");
    }

    @Test
    void addSummoner_tooManySummoners() {
        // Arrange
        LinkedHashSet<String> fullPuuids = new LinkedHashSet<>();
        IntStream.rangeClosed(1, 10).forEach(i -> fullPuuids.add("puuid-" + i));
        Board board = new Board()
                .setId("board-123")
                .setPuuids(fullPuuids);

        // Act & Assert
        assertThatThrownBy(() -> board.addSummoner("puuid-11"))
                .isInstanceOf(TooManySummonersException.class);
        assertThat(board.getPuuids()).hasSize(10).doesNotContain("puuid-11");
    }

    @Test
    void removeSummoner() {
        // Arrange
        Board board = new Board()
                .setId("board-123")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")));

        // Act
        board.removeSummoner("puuid-1");

        // Assert
        assertThat(board.getPuuids()).containsExactly("puuid-2");
    }

    @Test
    void removeSummoner_notInBoard() {
        // Arrange
        Board board = new Board()
                .setId("board-123")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1")));

        // Act
        board.removeSummoner("puuid-absent");

        // Assert
        assertThat(board.getPuuids()).containsExactly("puuid-1");
    }
}