package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.ArenaCompletionDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.ArenaCompletionMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.ArenaCompletionMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArenaCompletionMongoAdapterTest {

    @Mock
    private ArenaCompletionMongoRepository repository;

    @Spy
    private ArenaCompletionMongoMapper mapper = Mappers.getMapper(ArenaCompletionMongoMapper.class);

    @InjectMocks
    private ArenaCompletionMongoAdapter arenaCompletionMongoAdapter;

    @Test
    void findArenaCompletion() {
        // Arrange
        String puuid = "puuid-1234";
        Set<String> expectedChampions = Set.of("121", "18");
        ArenaCompletionDocument document = new ArenaCompletionDocument()
                .setPuuid(puuid)
                .setChampions(expectedChampions);
        when(repository.findByPuuid(puuid)).thenReturn(Optional.of(document));

        // Act
        Set<String> result = arenaCompletionMongoAdapter.findArenaCompletion(puuid);

        // Assert
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedChampions);
    }

    @Test
    void findArenaCompletion_notFound() {
        // Arrange
        String puuid = "puuid-inconnu";
        when(repository.findByPuuid(puuid)).thenReturn(Optional.empty());

        // Act
        Set<String> result = arenaCompletionMongoAdapter.findArenaCompletion(puuid);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void saveArenaCompletion() {
        // Arrange
        String puuid = "puuid-1234";
        Set<String> champions = Set.of("121", "18");
        ArenaCompletionDocument expectedDocument = new ArenaCompletionDocument()
                .setPuuid(puuid)
                .setChampions(champions);

        // Act
        arenaCompletionMongoAdapter.saveArenaCompletion(champions, puuid);

        // Assert
        verify(repository).save(expectedDocument);
    }
}