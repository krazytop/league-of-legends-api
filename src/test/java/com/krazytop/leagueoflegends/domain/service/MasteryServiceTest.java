package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.domain.port.out.mastery.MasteryRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.mastery.MasteryRiotPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MasteryServiceTest {

    @Mock
    private MasteryRiotPort riotPort;

    @Mock
    private MasteryRepositoryPort repositoryPort;

    @InjectMocks
    private MasteryService masteryService;

    @Test
    void getMasteries() {
        // Arrange
        String puuid = "test-puuid-123";
        List<Mastery> expectedMasteries = List.of(
                mock(Mastery.class),
                mock(Mastery.class)
        );
        when(repositoryPort.findMasteries(puuid)).thenReturn(expectedMasteries);

        // Act
        List<Mastery> result = masteryService.getMasteries(puuid);

        // Assert
        assertThat(result).isEqualTo(expectedMasteries);
        verify(repositoryPort).findMasteries(puuid);
        verifyNoInteractions(riotPort);
    }

    @Test
    void updateMasteries() {
        // Arrange
        String puuid = "test-puuid-123";
        List<Mastery> fetchedMasteries = List.of(mock(Mastery.class));
        when(riotPort.fetchMasteries(EnumRegion.EUW1, puuid)).thenReturn(fetchedMasteries);

        // Act
        masteryService.updateMasteries(puuid);

        // Assert
        verify(riotPort).fetchMasteries(EnumRegion.EUW1, puuid);
        verify(repositoryPort).saveMasteries(fetchedMasteries, puuid);
    }
}