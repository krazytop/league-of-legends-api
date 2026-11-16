package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.domain.service.MasteryService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.MasteryRestMapper;
import com.krazytop.leagueoflegends.model.generated.MasteryDTO;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MasteryControllerTest {

    @Mock
    private MasteryService service;

    @Spy
    private MasteryRestMapper mapper = Mappers.getMapper(MasteryRestMapper.class);

    @InjectMocks
    private MasteryController masteryController;

    @Test
    void getMasteries() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant lastPlayTime = Instant.parse("2026-10-01T12:00:00Z");

        Mastery mastery = new Mastery()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        MasteryDTO expectedDto = new MasteryDTO()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        when(service.getMasteries(puuid)).thenReturn(List.of(mastery));

        // Act
        ResponseEntity<List<MasteryDTO>> response = masteryController.getMasteries(puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(List.of(expectedDto));
        verify(service).getMasteries(puuid);
    }

    @Test
    void updateMasteries() {
        // Arrange
        String puuid = "test-puuid-123";

        // Act
        ResponseEntity<Void> response = masteryController.updateMasteries(puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(service).updateMasteries(puuid);
    }
}