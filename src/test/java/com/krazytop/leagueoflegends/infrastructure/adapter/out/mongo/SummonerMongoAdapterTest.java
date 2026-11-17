package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.SummonerDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.SummonerMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.SummonerMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummonerMongoAdapterTest {

    @Mock
    private SummonerMongoRepository repository;

    @Spy
    private SummonerMongoMapper mapper = Mappers.getMapper(SummonerMongoMapper.class);

    @InjectMocks
    private SummonerMongoAdapter summonerMongoAdapter;

    @Test
    void findSummoner_byPuuid() {
        // Arrange
        String puuid = "test-puuid-123";
        SummonerDocument document = new SummonerDocument()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion("EUW1")
                .setLevel(450)
                .setIcon(4567);

        when(repository.findByPuuid(puuid)).thenReturn(Optional.ofNullable(document));

        Summoner expectedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon(4567);

        // Act
        Optional<Summoner> result = summonerMongoAdapter.findSummoner(puuid);

        // Assert
        assertThat(result).contains(expectedSummoner);
        verify(repository).findByPuuid(puuid);
    }

    @Test
    void findSummoner_byPuuid_whenNotFound_returnsEmpty() {
        // Arrange
        String puuid = "unknown-puuid";
        when(repository.findByPuuid(puuid)).thenReturn(Optional.empty());

        // Act
        Optional<Summoner> result = summonerMongoAdapter.findSummoner(puuid);

        // Assert
        assertThat(result).isEmpty();
        verify(repository).findByPuuid(puuid);
    }

    @Test
    void findSummoner_byTagAndName() {
        // Arrange
        String tag = "EUW";
        String name = "Krazytop";
        SummonerDocument document = new SummonerDocument()
                .setPuuid("test-puuid-123")
                .setName(name)
                .setTag(tag)
                .setRegion("EUW1")
                .setLevel(450)
                .setIcon(4567);

        when(repository.findFirstByTagAndName(tag, name)).thenReturn(Optional.of(document));

        Summoner expectedSummoner = new Summoner()
                .setPuuid("test-puuid-123")
                .setName(name)
                .setTag(tag)
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon(4567);

        // Act
        Optional<Summoner> result = summonerMongoAdapter.findSummoner(tag, name);

        // Assert
        assertThat(result).contains(expectedSummoner);
        verify(repository).findFirstByTagAndName(tag, name);
    }

    @Test
    void findSummoner_byTagAndName_whenNotFound_returnsEmpty() {
        // Arrange
        String tag = "EUW";
        String name = "Unknown";
        when(repository.findFirstByTagAndName(tag, name)).thenReturn(Optional.empty());

        // Act
        Optional<Summoner> result = summonerMongoAdapter.findSummoner(tag, name);

        // Assert
        assertThat(result).isEmpty();
        verify(repository).findFirstByTagAndName(tag, name);
    }

    @Test
    void updateSummoner() {
        // Arrange
        Summoner summonerToSave = new Summoner()
                .setPuuid("test-puuid-123")
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon(4567);

        SummonerDocument expectedDocument = new SummonerDocument()
                .setPuuid("test-puuid-123")
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion("EUW1")
                .setLevel(450)
                .setIcon(4567);

        when(repository.save(any(SummonerDocument.class))).thenReturn(expectedDocument);

        // Act
        Summoner result = summonerMongoAdapter.saveSummoner(summonerToSave);

        // Assert
        ArgumentCaptor<SummonerDocument> documentCaptor = ArgumentCaptor.captor();
        verify(repository).save(documentCaptor.capture());
        assertThat(documentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedDocument);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(summonerToSave);
    }
}