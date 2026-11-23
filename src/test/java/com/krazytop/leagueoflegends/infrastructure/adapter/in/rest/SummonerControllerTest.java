package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.service.SummonerService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.SummonerRestMapper;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummonerControllerTest {

    @Mock
    private SummonerService service;

    @Spy
    private SummonerRestMapper mapper = Mappers.getMapper(SummonerRestMapper.class);

    @InjectMocks
    private SummonerController controller;

    @Test
    void getSummonerByTagAndName() {
        // Arrange
        String tag = "EUW";
        String name = "Krazytop";
        Instant updateDate = Instant.parse("2026-11-17T12:00:00Z");

        Summoner summoner = new Summoner()
                .setPuuid("test-puuid-123")
                .setName(name)
                .setTag(tag)
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567")
                .setUpdateDate(updateDate)
                .setSpentTime(123456L)
                .setPlayedSeasons(Set.of(13, 14, 15));

        when(service.getOrFetchSummoner(tag, name)).thenReturn(summoner);

        SummonerDTO expectedDto = new SummonerDTO()
                .setPuuid("test-puuid-123")
                .setName(name)
                .setTag(tag)
                .setRegion("EUW1")
                .setLevel(450)
                .setIcon("4567")
                .setUpdateDate(updateDate)
                .setSpentTime(123456L)
                .setPlayedSeasonsOrSets(Set.of(13, 14, 15));

        // Act
        ResponseEntity<SummonerDTO> response = controller.getSummonerByTagAndName(tag, name);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);
        verify(service).getOrFetchSummoner(tag, name);
    }

    @Test
    void getSummonerByPuuid() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant updateDate = Instant.parse("2026-11-17T12:00:00Z");

        Summoner summoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567")
                .setUpdateDate(updateDate)
                .setSpentTime(123456L)
                .setPlayedSeasons(Set.of(13, 14, 15));

        when(service.getOrFetchSummoner(puuid)).thenReturn(summoner);

        SummonerDTO expectedDto = new SummonerDTO()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion("EUW1")
                .setLevel(450)
                .setIcon("4567")
                .setUpdateDate(updateDate)
                .setSpentTime(123456L)
                .setPlayedSeasonsOrSets(Set.of(13, 14, 15));

        // Act
        ResponseEntity<SummonerDTO> response = controller.getSummonerByPuuid(puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);
        verify(service).getOrFetchSummoner(puuid);
    }

    @Test
    void updateSummoner() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant updateDate = Instant.parse("2026-11-17T12:00:00Z");

        Summoner updatedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(451)
                .setIcon("4567")
                .setUpdateDate(updateDate)
                .setSpentTime(123456L)
                .setPlayedSeasons(Set.of(13, 14, 15));

        when(service.updateSummoner(puuid)).thenReturn(updatedSummoner);

        SummonerDTO expectedDto = new SummonerDTO()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion("EUW1")
                .setLevel(451)
                .setIcon("4567")
                .setUpdateDate(updateDate)
                .setSpentTime(123456L)
                .setPlayedSeasonsOrSets(Set.of(13, 14, 15));

        // Act
        ResponseEntity<SummonerDTO> response = controller.updateSummoner(puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);
        verify(service).updateSummoner(puuid);
    }
}