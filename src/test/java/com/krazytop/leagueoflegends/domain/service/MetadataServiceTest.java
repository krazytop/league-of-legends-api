package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.Metadata;
import com.krazytop.leagueoflegends.domain.port.out.metadata.MetadataRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataServiceTest {

    @Mock
    private MetadataRepositoryPort repositoryPort;

    @InjectMocks
    private MetadataService metadataService;

    @Test
    void findMetadata() {
        // Arrange
        Metadata expectedMetadata = new Metadata()
                .setCurrentSeason(16);
        when(repositoryPort.findMetadata()).thenReturn(expectedMetadata);

        // Act
        Metadata result = metadataService.getMetadata();

        // Assert
        assertThat(result).isEqualTo(expectedMetadata);
        verify(repositoryPort).findMetadata();
    }

    @Test
    void getCurrentSeasonOrSet() {
        // Arrange
        Metadata metadata = new Metadata()
                .setCurrentSeason(16);
        when(repositoryPort.findMetadata()).thenReturn(metadata);

        // Act
        int result = metadataService.getCurrentSeason();

        // Assert
        assertThat(result).isEqualTo(16);
        verify(repositoryPort).findMetadata();
    }
}