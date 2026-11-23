package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.BoardDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.BoardMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.BoardMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardMongoAdapterTest {

    @Mock
    private BoardMongoRepository repository;

    @Spy
    private BoardMongoMapper mapper = Mappers.getMapper(BoardMongoMapper.class);

    @InjectMocks
    private BoardMongoAdapter boardMongoAdapter;

    @Test
    void findBoard() {
        // Arrange
        String boardId = "board-123";
        Instant creationDate = Instant.parse("2026-11-22T17:00:00Z");
        Instant updateDate = Instant.parse("2026-11-22T18:00:00Z");

        BoardDocument document = new BoardDocument()
                .setId(boardId)
                .setName("Team Ranked")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")))
                .setCreationDate(creationDate)
                .setUpdateDate(updateDate);

        Board expectedBoard = new Board()
                .setId(boardId)
                .setName("Team Ranked")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")))
                .setCreationDate(creationDate)
                .setUpdateDate(updateDate);

        when(repository.findById(boardId)).thenReturn(Optional.of(document));

        // Act
        Optional<Board> result = boardMongoAdapter.findBoard(boardId);

        // Assert
        assertThat(result).contains(expectedBoard);
    }

    @Test
    void findBoard_notFound() {
        // Arrange
        String boardId = "board-inconnu";
        when(repository.findById(boardId)).thenReturn(Optional.empty());

        // Act
        Optional<Board> result = boardMongoAdapter.findBoard(boardId);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void saveBoard() {
        // Arrange
        Instant creationDate = Instant.parse("2026-11-22T17:00:00Z");
        Instant updateDate = Instant.parse("2026-11-22T18:00:00Z");

        Board board = new Board()
                .setId("board-123")
                .setName("Team Ranked")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")))
                .setCreationDate(creationDate)
                .setUpdateDate(updateDate);

        BoardDocument expectedDocument = new BoardDocument()
                .setId("board-123")
                .setName("Team Ranked")
                .setPuuids(new LinkedHashSet<>(List.of("puuid-1", "puuid-2")))
                .setCreationDate(creationDate)
                .setUpdateDate(updateDate);

        when(repository.save(expectedDocument)).thenReturn(expectedDocument);

        // Act
        Board result = boardMongoAdapter.saveBoard(board);

        // Assert
        assertThat(result).isEqualTo(board);
        verify(repository).save(expectedDocument);
    }

    @Test
    void deleteBoard() {
        // Arrange
        String boardId = "board-123";

        // Act
        boardMongoAdapter.deleteBoard(boardId);

        // Assert
        verify(repository).deleteById(boardId);
    }
}