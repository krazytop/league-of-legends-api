package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.service.ArenaCompletionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArenaCompletionControllerTest {

    private static final String PUUID = "puuid-123";

    @Mock
    private ArenaCompletionService service;

    @InjectMocks
    private ArenaCompletionController controller;

    @Test
    void getArenaCompletion_shouldReturnOkWithChampions() {
        // Arrange
        Set<String> champions = Set.of("Ahri", "LeeSin", "Zed");
        when(service.getArenaCompletion(PUUID)).thenReturn(champions);

        // Act
        ResponseEntity<Set<String>> response = controller.getArenaCompletion(PUUID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactlyInAnyOrder("Ahri", "LeeSin", "Zed");
        verify(service).getArenaCompletion(PUUID);
    }

    @Test
    void getArenaCompletion_shouldReturnOkWithEmptySet_whenNoChampionsFound() {
        // Arrange
        when(service.getArenaCompletion(PUUID)).thenReturn(Set.of());

        // Act
        ResponseEntity<Set<String>> response = controller.getArenaCompletion(PUUID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(service).getArenaCompletion(PUUID);
    }
}