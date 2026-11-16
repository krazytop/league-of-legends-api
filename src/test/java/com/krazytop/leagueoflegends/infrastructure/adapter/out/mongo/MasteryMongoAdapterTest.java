package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MasteriesDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.MasterySubdocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.MasteryMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.MasteryMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MasteryMongoAdapterTest {

    @Mock
    private MasteryMongoRepository repository;

    @Spy
    private MasteryMongoMapper mapper = Mappers.getMapper(MasteryMongoMapper.class);

    @InjectMocks
    private MasteryMongoAdapter masteryMongoAdapter;

    @Test
    void findMasteries() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant lastPlayTime = Instant.parse("2026-10-01T12:00:00Z");

        MasterySubdocument subdocument = new MasterySubdocument()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        MasteriesDocument document = new MasteriesDocument()
                .setPuuid(puuid)
                .setChampions(List.of(subdocument));

        Mastery expectedMastery = new Mastery()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        when(repository.findByPuuid(puuid)).thenReturn(Optional.of(document));

        // Act
        List<Mastery> result = masteryMongoAdapter.findMasteries(puuid);

        // Assert
        assertThat(result).containsExactly(expectedMastery);
        verify(repository).findByPuuid(puuid);
    }

    @Test
    void findMasteries_whenNotFound() {
        // Arrange
        String puuid = "unknown-puuid";
        when(repository.findByPuuid(puuid)).thenReturn(Optional.empty());

        // Act
        List<Mastery> result = masteryMongoAdapter.findMasteries(puuid);

        // Assert
        assertThat(result).isEmpty();
        verify(repository).findByPuuid(puuid);
    }

    @Test
    void saveMasteries() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant lastPlayTime = Instant.parse("2026-10-01T12:00:00Z");

        Mastery mastery = new Mastery()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        MasterySubdocument expectedSubdocument = new MasterySubdocument()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        MasteriesDocument expectedDocument = new MasteriesDocument()
                .setPuuid(puuid)
                .setChampions(List.of(expectedSubdocument));

        // Act
        masteryMongoAdapter.saveMasteries(List.of(mastery), puuid);

        // Assert
        verify(repository).save(expectedDocument);
    }
}