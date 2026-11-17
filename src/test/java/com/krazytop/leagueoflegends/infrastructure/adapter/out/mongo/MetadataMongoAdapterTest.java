package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Metadata;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MetadataDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.MetadataMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.MetadataMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataMongoAdapterTest {

    @Mock
    private MetadataMongoRepository repository;

    @Spy
    private MetadataMongoMapper mapper = Mappers.getMapper(MetadataMongoMapper.class);

    @InjectMocks
    private MetadataMongoAdapter metadataMongoAdapter;

    @Test
    void findMetadata() {
        // Arrange
        MetadataDocument document = new MetadataDocument()
                .setId(1)
                .setCurrentSeason(16)
                .setCurrentPatch("16.1")
                .setAllPatches(Set.of("16.1", "16.0", "15.21"));

        Metadata expectedMetadata = new Metadata()
                .setCurrentSeason(16)
                .setCurrentPatch("16.1");

        when(repository.findById(1)).thenReturn(document);

        // Act
        Metadata result = metadataMongoAdapter.findMetadata();

        // Assert
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedMetadata);
        verify(repository).findById(1);
    }
}