package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.port.out.arenacompletion.ArenaCompletionRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArenaCompletionServiceTest {

    private static final String PUUID = "puuid-123";

    @Mock
    private ArenaCompletionRepositoryPort repositoryPort;

    @InjectMocks
    private ArenaCompletionService service;

    @Test
    void getArenaCompletion_shouldReturnChampionsFromRepository() {
        // Arrange
        Set<String> expectedChampions = Set.of("Ahri", "LeeSin", "Zed");
        when(repositoryPort.findArenaCompletion(PUUID)).thenReturn(expectedChampions);

        // Act
        Set<String> result = service.getArenaCompletion(PUUID);

        // Then
        assertThat(result).containsExactlyInAnyOrder("Ahri", "LeeSin", "Zed");
        verify(repositoryPort).findArenaCompletion(PUUID);
    }

    @Test
    void getArenaCompletion_shouldReturnEmptySet_whenNoChampionsCompleted() {
        // Arrange
        when(repositoryPort.findArenaCompletion(PUUID)).thenReturn(Set.of());

        // Act
        Set<String> result = service.getArenaCompletion(PUUID);

        // Then
        assertThat(result).isEmpty();
        verify(repositoryPort).findArenaCompletion(PUUID);
    }

    @Test
    void saveArenaCompletion_shouldDelegateToRepository() {
        // Arrange
        Set<String> champions = Set.of("Ahri", "Jinx");

        // Act
        service.saveArenaCompletion(champions, PUUID);

        // Then
        verify(repositoryPort).saveArenaCompletion(champions, PUUID);
    }
}