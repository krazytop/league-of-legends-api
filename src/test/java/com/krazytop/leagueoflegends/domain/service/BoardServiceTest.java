package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.exception.SummonerAlreadyInBoardException;
import com.krazytop.leagueoflegends.domain.exception.TooManySummonersException;
import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.domain.model.BoardUpdateRequest;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.port.out.board.BoardRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static com.krazytop.leagueoflegends.cucumber.config.CucumberTimeConfig.INSTANT_ACTUEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepositoryPort repositoryPort;

    @Mock
    private SummonerService summonerService;

    @Mock
    private RankService rankService;

    @Mock
    private MasteryService masteryService;

    @Mock
    private MatchService matchService;

    @Spy
    private Clock clock = Clock.fixed(INSTANT_ACTUEL, ZoneOffset.UTC);

    @InjectMocks
    private BoardService boardService;

    @Test
    void createBoard() {
        // Arrange
        when(repositoryPort.saveBoard(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Board result = boardService.createBoard();

        // Assert
        assertThat(result.getId()).isNotBlank();
        assertThat(result.getName()).isEqualTo("Nouveau Board");
        assertThat(result.getUpdateDate()).isEqualTo(INSTANT_ACTUEL);
        assertThat(result.getPuuids()).isEmpty();
        verify(repositoryPort).saveBoard(result);
    }

    @Test
    void getBoard() {
        // Arrange
        Board board = new Board().setId("board-123").setName("Team Ranked");
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(board));

        // Act
        Board result = boardService.getBoard("board-123");

        // Assert
        assertThat(result).isSameAs(board);
    }

    @Test
    void getBoard_notFound() {
        // Arrange
        when(repositoryPort.findBoard("board-inconnu")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> boardService.getBoard("board-inconnu"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Le board board-inconnu n'existe pas");
    }

    @Test
    void patchBoard() {
        // Arrange
        Board existing = new Board().setId("board-123").setName("Ancien Nom");
        BoardUpdateRequest request = new BoardUpdateRequest().setName("  Nouveau Nom  ");
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(existing));
        when(repositoryPort.saveBoard(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Board result = boardService.patchBoard("board-123", request);

        // Assert
        assertThat(result.getName()).isEqualTo("Nouveau Nom");
        verify(repositoryPort).saveBoard(existing);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t\n"})
    void patchBoard_blankName(String blankName) {
        // Arrange
        Board existing = new Board().setId("board-123").setName("Nom Conservé");
        BoardUpdateRequest request = new BoardUpdateRequest().setName(blankName);
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(existing));
        when(repositoryPort.saveBoard(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Board result = boardService.patchBoard("board-123", request);

        // Assert
        assertThat(result.getName()).isEqualTo("Nom Conservé");
        verify(repositoryPort).saveBoard(existing);
    }

    @Test
    void deleteBoard() {
        // Act
        boardService.deleteBoard("board-123");

        // Assert
        verify(repositoryPort).deleteBoard("board-123");
    }

    @Test
    void updateBoard() {
        // Arrange
        Board board = new Board()
                .setId("board-123")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")))
                .setUpdateDate(Instant.parse("2026-11-20T10:00:00Z"));
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(board));

        // Act
        boardService.updateBoard("board-123");

        // Assert
        verify(summonerService).updateSummoner("puuid-1");
        verify(rankService).updateRanks("puuid-1");
        verify(masteryService).updateMasteries("puuid-1");
        verify(matchService).updateMatches("puuid-1", false);

        verify(summonerService).updateSummoner("puuid-2");
        verify(rankService).updateRanks("puuid-2");
        verify(masteryService).updateMasteries("puuid-2");
        verify(matchService).updateMatches("puuid-2", false);

        ArgumentCaptor<Board> boardCaptor = ArgumentCaptor.forClass(Board.class);
        verify(repositoryPort).saveBoard(boardCaptor.capture());
        assertThat(boardCaptor.getValue().getUpdateDate()).isEqualTo(INSTANT_ACTUEL);
    }

    @Test
    void addSummonerToBoard() {
        // Arrange
        Board board = new Board().setId("board-123");
        Summoner summoner = new Summoner().setPuuid("puuid-1234").setName("Krazytop").setTag("EUW");
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(board));
        when(summonerService.getOrFetchSummoner("EUW", "Krazytop")).thenReturn(summoner);

        // Act
        Summoner result = boardService.addSummonerToBoard("board-123", "EUW", "Krazytop");

        // Assert
        assertThat(result).isSameAs(summoner);
        assertThat(board.getPuuids()).containsExactly("puuid-1234");
        verify(repositoryPort).saveBoard(board);
    }

    @Test
    void addSummonerToBoard_alreadyInBoard() {
        // Arrange
        Board board = new Board()
                .setId("board-123")
                .setPuuids(new LinkedHashSet<>(Set.of("puuid-1234")));
        Summoner summoner = new Summoner().setPuuid("puuid-1234").setName("Krazytop").setTag("EUW");
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(board));
        when(summonerService.getOrFetchSummoner("EUW", "Krazytop")).thenReturn(summoner);

        // Act & Assert
        assertThatThrownBy(() -> boardService.addSummonerToBoard("board-123", "EUW", "Krazytop"))
                .isInstanceOf(SummonerAlreadyInBoardException.class);
        verify(repositoryPort, never()).saveBoard(any());
    }

    @Test
    void addSummonerToBoard_tooManySummoners() {
        // Arrange
        LinkedHashSet<String> fullPuuids = new LinkedHashSet<>();
        IntStream.rangeClosed(1, 10).forEach(i -> fullPuuids.add("puuid-" + i));
        Board board = new Board().setId("board-123").setPuuids(fullPuuids);
        Summoner summoner = new Summoner().setPuuid("puuid-11").setName("OnziemeJoueur").setTag("EUW");
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(board));
        when(summonerService.getOrFetchSummoner("EUW", "OnziemeJoueur")).thenReturn(summoner);

        // Act & Assert
        assertThatThrownBy(() -> boardService.addSummonerToBoard("board-123", "EUW", "OnziemeJoueur"))
                .isInstanceOf(TooManySummonersException.class);
        verify(repositoryPort, never()).saveBoard(any());
    }

    @Test
    void removeSummonerFromBoard() {
        // Arrange
        Board board = new Board()
                .setId("board-123")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1234", "puuid-5678")));
        when(repositoryPort.findBoard("board-123")).thenReturn(Optional.of(board));

        // Act
        boardService.removeSummonerFromBoard("board-123", "puuid-1234");

        // Assert
        assertThat(board.getPuuids()).containsExactly("puuid-5678");
        verify(repositoryPort).saveBoard(board);
    }
}