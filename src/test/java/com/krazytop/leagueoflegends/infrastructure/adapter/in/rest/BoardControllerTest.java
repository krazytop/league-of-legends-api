package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.domain.model.BoardUpdateRequest;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.service.BoardService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.BoardRestMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.SummonerRestMapper;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import com.krazytop.leagueoflegends.model.generated.BoardUpdateRequestDTO;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {

    @Mock
    private BoardService service;

    @Spy
    private BoardRestMapper boardMapper = Mappers.getMapper(BoardRestMapper.class);

    @Spy
    private SummonerRestMapper summonerMapper = Mappers.getMapper(SummonerRestMapper.class);

    @InjectMocks
    private BoardController boardController;

    @Test
    void createBoard() {
        // Arrange
        Instant updateDate = Instant.parse("2026-11-22T18:00:00Z");
        Board createdBoard = new Board()
                .setId("board-123")
                .setName("Nouveau Board")
                .setUpdateDate(updateDate);
        BoardDTO expectedDTO = boardMapper.toDTO(createdBoard);
        when(service.createBoard()).thenReturn(createdBoard);

        // Act
        ResponseEntity<BoardDTO> response = boardController.createBoard();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedDTO);
        verify(service).createBoard();
    }

    @Test
    void getBoard() {
        // Arrange
        String boardId = "board-123";
        Instant updateDate = Instant.parse("2026-11-22T18:00:00Z");
        Board board = new Board()
                .setId(boardId)
                .setName("Team Ranked")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")))
                .setUpdateDate(updateDate);
        BoardDTO expectedDTO = boardMapper.toDTO(board);
        when(service.getBoard(boardId)).thenReturn(board);

        // Act
        ResponseEntity<BoardDTO> response = boardController.getBoard(boardId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedDTO);
        verify(service).getBoard(boardId);
    }

    @Test
    void patchBoard() {
        // Arrange
        String boardId = "board-123";
        BoardUpdateRequestDTO requestDTO = new BoardUpdateRequestDTO().name("Les Tryharders");
        BoardUpdateRequest expectedRequest = new BoardUpdateRequest().setName("Les Tryharders");
        Board updatedBoard = new Board()
                .setId(boardId)
                .setName("Les Tryharders")
                .setUpdateDate(Instant.parse("2026-11-22T18:00:00Z"));
        BoardDTO expectedDTO = boardMapper.toDTO(updatedBoard);
        when(service.patchBoard(boardId, expectedRequest)).thenReturn(updatedBoard);

        // Act
        ResponseEntity<BoardDTO> response = boardController.patchBoard(boardId, requestDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedDTO);
        verify(service).patchBoard(boardId, expectedRequest);
    }

    @Test
    void deleteBoard() {
        // Arrange
        String boardId = "board-123";

        // Act
        ResponseEntity<Void> response = boardController.deleteBoard(boardId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        verify(service).deleteBoard(boardId);
    }

    @Test
    void updateBoard() {
        // Arrange
        String boardId = "board-123";

        // Act
        ResponseEntity<Void> response = boardController.updateBoard(boardId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        verify(service).updateBoard(boardId);
    }

    @Test
    void addSummonerToBoard() {
        // Arrange
        String boardId = "board-123";
        String tag = "EUW";
        String name = "Krazytop";
        Summoner summoner = new Summoner()
                .setPuuid("puuid-1234")
                .setTag(tag)
                .setName(name)
                .setLevel(450);
        SummonerDTO expectedDTO = summonerMapper.toDTO(summoner);
        when(service.addSummonerToBoard(boardId, tag, name)).thenReturn(summoner);

        // Act
        ResponseEntity<SummonerDTO> response = boardController.addSummonerToBoard(boardId, tag, name);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedDTO);
        verify(service).addSummonerToBoard(boardId, tag, name);
    }

    @Test
    void removeSummonerFromBoard() {
        // Arrange
        String boardId = "board-123";
        String puuid = "puuid-1234";

        // Act
        ResponseEntity<Void> response = boardController.removeSummonerFromBoard(boardId, puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        verify(service).removeSummonerFromBoard(boardId, puuid);
    }
}