package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.RankInformations;
import com.krazytop.leagueoflegends.domain.model.SeasonOrSetRanks;
import com.krazytop.leagueoflegends.domain.port.out.rank.RankRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.rank.RankRiotPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static com.krazytop.leagueoflegends.cucumber.config.CucumberTimeConfig.INSTANT_ACTUEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RankServiceTest {

    @Mock
    private RankRiotPort riotPort;

    @Mock
    private RankRepositoryPort repositoryPort;

    @Mock
    private MetadataService metadataService;

    @Spy
    private Clock clock = Clock.fixed(INSTANT_ACTUEL, ZoneOffset.UTC);

    @InjectMocks
    private RankService rankService;

    @Test
    void getRanks() {
        // Arrange
        String puuid = "test-puuid-123";
        List<SeasonOrSetRanks> expectedRanks = List.of(mock(SeasonOrSetRanks.class));
        when(repositoryPort.findRanks(puuid)).thenReturn(expectedRanks);

        // Act
        List<SeasonOrSetRanks> result = rankService.getRanks(puuid);

        // Assert
        assertThat(result).isEqualTo(expectedRanks);
        verify(repositoryPort).findRanks(puuid);
        verifyNoInteractions(riotPort);
    }

    @Test
    void updateRanks() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant oldDate = Instant.parse("2026-10-01T12:00:00Z");

        SeasonOrSetRanks previousSeason = new SeasonOrSetRanks()
                .setSeasonOrSetNumber(15)
                .setQueueRanks(List.of());

        SeasonOrSetRanks existingCurrentSeason = new SeasonOrSetRanks()
                .setSeasonOrSetNumber(16)
                .setQueueRanks(List.of(
                        new QueueRanks()
                                .setName("RANKED_SOLO_5x5")
                                .setRankInformations(List.of(
                                        new RankInformations()
                                                .setDate(oldDate)
                                                .setTier("DIAMOND")
                                                .setRank("II")
                                                .setLeaguePoints(50)
                                                .setWins(100)
                                                .setLosses(90)))
                ));

        List<QueueRanks> fetchedQueues = List.of(
                new QueueRanks()
                        .setName("RANKED_SOLO_5x5")
                        .setRankInformations(List.of(
                                new RankInformations()
                                        .setDate(INSTANT_ACTUEL)
                                        .setTier("DIAMOND")
                                        .setRank("II")
                                        .setLeaguePoints(75)
                                        .setWins(101)
                                        .setLosses(90))),
                new QueueRanks()
                        .setName("RANKED_FLEX_SR")
                        .setRankInformations(List.of(
                                new RankInformations()
                                        .setDate(INSTANT_ACTUEL)
                                        .setTier("EMERALD")
                                        .setRank("I")
                                        .setLeaguePoints(20)
                                        .setWins(15)
                                        .setLosses(10)
                        ))
        );

        List<SeasonOrSetRanks> expectedSavedRanks = List.of(
                previousSeason,
                new SeasonOrSetRanks()
                        .setSeasonOrSetNumber(16)
                        .setQueueRanks(List.of(
                                new QueueRanks()
                                        .setName("RANKED_SOLO_5x5")
                                        .setRankInformations(List.of(
                                                new RankInformations()
                                                        .setDate(oldDate)
                                                        .setTier("DIAMOND")
                                                        .setRank("II")
                                                        .setLeaguePoints(50)
                                                        .setWins(100)
                                                        .setLosses(90),
                                                new RankInformations()
                                                        .setDate(INSTANT_ACTUEL)
                                                        .setTier("DIAMOND")
                                                        .setRank("II")
                                                        .setLeaguePoints(75)
                                                        .setWins(101)
                                                        .setLosses(90))),
                                new QueueRanks()
                                        .setName("RANKED_FLEX_SR")
                                        .setRankInformations(List.of(
                                                new RankInformations()
                                                        .setDate(INSTANT_ACTUEL)
                                                        .setTier("EMERALD")
                                                        .setRank("I")
                                                        .setLeaguePoints(20)
                                                        .setWins(15)
                                                        .setLosses(10)
                                        ))
                        ))
        );

        when(metadataService.getCurrentSeason()).thenReturn(16);
        when(riotPort.fetchRanks(EnumRegion.EUW1, puuid)).thenReturn(fetchedQueues);
        when(repositoryPort.findRanks(puuid)).thenReturn(List.of(previousSeason, existingCurrentSeason));

        // Act
        rankService.updateRanks(puuid);

        // Assert
        ArgumentCaptor<List<SeasonOrSetRanks>> ranksCaptor = ArgumentCaptor.captor();
        verify(repositoryPort).saveRanks(ranksCaptor.capture(), eq(puuid));
        assertThat(ranksCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedSavedRanks);
    }

    @Test
    void updateRanks_whenNoNewGamesPlayed() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant oldDate = Instant.parse("2026-10-01T12:00:00Z");
        Instant newDate = Instant.parse("2026-10-02T12:00:00Z");

        List<QueueRanks> fetchedQueueRankWithNoChanges = List.of(
                new QueueRanks()
                        .setName("RANKED_SOLO_5x5")
                        .setRankInformations(List.of(
                                new RankInformations()
                                        .setDate(newDate)
                                        .setTier("DIAMOND")
                                        .setRank("II")
                                        .setLeaguePoints(50)
                                        .setWins(100)
                                        .setLosses(90)
                        )));

        List<SeasonOrSetRanks> existingSeasonsRanks = List.of(
                new SeasonOrSetRanks()
                        .setSeasonOrSetNumber(16)
                        .setQueueRanks(List.of(
                                new QueueRanks()
                                        .setName("RANKED_SOLO_5x5")
                                        .setRankInformations(List.of(
                                                new RankInformations()
                                                        .setDate(oldDate)
                                                        .setTier("DIAMOND")
                                                        .setRank("II")
                                                        .setLeaguePoints(50)
                                                        .setWins(100)
                                                        .setLosses(90)
                                        ))
                        )));

        List<SeasonOrSetRanks> expectedSavedRanks = List.of(
                new SeasonOrSetRanks()
                        .setSeasonOrSetNumber(16)
                        .setQueueRanks(List.of(
                                new QueueRanks()
                                        .setName("RANKED_SOLO_5x5")
                                        .setRankInformations(List.of(
                                                new RankInformations()
                                                        .setDate(oldDate)
                                                        .setTier("DIAMOND")
                                                        .setRank("II")
                                                        .setLeaguePoints(50)
                                                        .setWins(100)
                                                        .setLosses(90)
                                        ))
                        )));

        when(metadataService.getCurrentSeason()).thenReturn(16);
        when(riotPort.fetchRanks(EnumRegion.EUW1, puuid)).thenReturn(fetchedQueueRankWithNoChanges);
        when(repositoryPort.findRanks(puuid)).thenReturn(existingSeasonsRanks);

        // Act
        rankService.updateRanks(puuid);

        // Assert
        verify(repositoryPort).saveRanks(expectedSavedRanks, puuid);
    }
}