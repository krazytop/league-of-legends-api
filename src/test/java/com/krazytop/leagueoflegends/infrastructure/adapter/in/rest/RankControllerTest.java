package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.RankInformations;
import com.krazytop.leagueoflegends.domain.model.SeasonRanks;
import com.krazytop.leagueoflegends.domain.service.RankService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.RankRestMapper;
import com.krazytop.leagueoflegends.model.generated.QueueRanksDTO;
import com.krazytop.leagueoflegends.model.generated.RankInformationsDTO;
import com.krazytop.leagueoflegends.model.generated.SeasonRanksDTO;
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
class RankControllerTest {

    @Mock
    private RankService service;

    @Spy
    private RankRestMapper mapper = Mappers.getMapper(RankRestMapper.class);

    @InjectMocks
    private RankController rankController;

    @Test
    void getRanks() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant date = Instant.parse("2026-10-01T12:00:00Z");

        RankInformations rankInfo = new RankInformations()
                .setDate(date)
                .setTier("DIAMOND")
                .setRank("II")
                .setLeaguePoints(75)
                .setWins(120)
                .setLosses(105);

        QueueRanks queueRanks = new QueueRanks()
                .setName("RANKED_SOLO_5x5")
                .setRankInformations(List.of(rankInfo));

        SeasonRanks seasonRanks = new SeasonRanks()
                .setSeason(16)
                .setQueueRanks(List.of(queueRanks));

        RankInformationsDTO expectedRankInfoDto = new RankInformationsDTO()
                .setDate(date)
                .setTier("DIAMOND")
                .setRank("II")
                .setLeaguePoints(75)
                .setWins(120)
                .setLosses(105);

        QueueRanksDTO expectedQueueRanksDto = new QueueRanksDTO()
                .setName("RANKED_SOLO_5x5")
                .setRankInformations(List.of(expectedRankInfoDto));

        SeasonRanksDTO expectedSeasonRanksDto = new SeasonRanksDTO()
                .setSeason(16)
                .setQueueRanks(List.of(expectedQueueRanksDto));

        when(service.getRanks(puuid)).thenReturn(List.of(seasonRanks));

        // Act
        ResponseEntity<List<SeasonRanksDTO>> response = rankController.getRanks(puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(List.of(expectedSeasonRanksDto));
        verify(service).getRanks(puuid);
    }

    @Test
    void updateRanks() {
        // Arrange
        String puuid = "test-puuid-123";

        // Act
        ResponseEntity<Void> response = rankController.updateRanks(puuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(service).updateRanks(puuid);
    }
}