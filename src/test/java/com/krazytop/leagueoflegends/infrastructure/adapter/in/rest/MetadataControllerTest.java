package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.Metadata;
import com.krazytop.leagueoflegends.domain.service.MetadataService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.MetadataRestMapper;
import com.krazytop.leagueoflegends.model.generated.MetadataDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataControllerTest {

    @Mock
    private MetadataService service;

    @Spy
    private MetadataRestMapper mapper = Mappers.getMapper(MetadataRestMapper.class);

    @InjectMocks
    private MetadataController metadataController;

    @Test
    void getMetadata() {
        // Arrange
        Metadata metadata = new Metadata()
                .setCurrentSeason(16)
                .setCurrentPatch("16.21");

        MetadataDTO expectedDto = new MetadataDTO()
                .setCurrentSeason(16)
                .setCurrentPatch("16.21");

        when(service.getMetadata()).thenReturn(metadata);

        // Act
        ResponseEntity<MetadataDTO> response = metadataController.getMetadata();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringCollectionOrderInFields("allPatches")
                .isEqualTo(expectedDto);
        verify(service).getMetadata();
    }
}