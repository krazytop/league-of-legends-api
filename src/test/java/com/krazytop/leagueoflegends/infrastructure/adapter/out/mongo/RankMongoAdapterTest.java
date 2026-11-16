package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.RankInformations;
import com.krazytop.leagueoflegends.domain.model.SeasonOrSetRanks;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.QueueRanksSubdocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.RankInformationsSubdocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.RanksDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.SeasonOrSetRanksSubdocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.RankMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.RankMongoRepository;
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
class RankMongoAdapterTest {

    @Mock
    private RankMongoRepository repository;

    @Spy
    private RankMongoMapper mapper = Mappers.getMapper(RankMongoMapper.class);

    @InjectMocks
    private RankMongoAdapter rankMongoAdapter;

    @Test
    void findRanks() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant date = Instant.parse("2026-10-01T12:00:00Z");

        RankInformationsSubdocument rankInfoSubdoc = new RankInformationsSubdocument()
                .setDate(date)
                .setTier("DIAMOND")
                .setRank("II")
                .setLeaguePoints(75)
                .setWins(120)
                .setLosses(105);

        QueueRanksSubdocument queueRanksSubdoc = new QueueRanksSubdocument()
                .setName("RANKED_SOLO_5x5")
                .setRankInformations(List.of(rankInfoSubdoc));

        SeasonOrSetRanksSubdocument seasonSubdoc = new SeasonOrSetRanksSubdocument()
                .setSeasonOrSetNumber(16)
                .setQueueRanks(List.of(queueRanksSubdoc));

        RanksDocument document = new RanksDocument()
                .setPuuid(puuid)
                .setSeasonOrSetRanks(List.of(seasonSubdoc));

        RankInformations expectedRankInfo = new RankInformations()
                .setDate(date)
                .setTier("DIAMOND")
                .setRank("II")
                .setLeaguePoints(75)
                .setWins(120)
                .setLosses(105);

        QueueRanks expectedQueueRanks = new QueueRanks()
                .setName("RANKED_SOLO_5x5")
                .setRankInformations(List.of(expectedRankInfo));

        SeasonOrSetRanks expectedSeasonRanks = new SeasonOrSetRanks()
                .setSeasonOrSetNumber(16)
                .setQueueRanks(List.of(expectedQueueRanks));

        when(repository.findByPuuid(puuid)).thenReturn(Optional.of(document));

        // Act
        List<SeasonOrSetRanks> result = rankMongoAdapter.findRanks(puuid);

        // Assert
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(List.of(expectedSeasonRanks));
        verify(repository).findByPuuid(puuid);
    }

    @Test
    void findRanks_whenNotFound() {
        // Arrange
        String puuid = "unknown-puuid";
        when(repository.findByPuuid(puuid)).thenReturn(Optional.empty());

        // Act
        List<SeasonOrSetRanks> result = rankMongoAdapter.findRanks(puuid);

        // Assert
        assertThat(result).isEmpty();
        verify(repository).findByPuuid(puuid);
    }

    @Test
    void saveRanks() {
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

        SeasonOrSetRanks seasonRanks = new SeasonOrSetRanks()
                .setSeasonOrSetNumber(16)
                .setQueueRanks(List.of(queueRanks));

        RankInformationsSubdocument expectedRankInfoSubdoc = new RankInformationsSubdocument()
                .setDate(date)
                .setTier("DIAMOND")
                .setRank("II")
                .setLeaguePoints(75)
                .setWins(120)
                .setLosses(105);

        QueueRanksSubdocument expectedQueueRanksSubdoc = new QueueRanksSubdocument()
                .setName("RANKED_SOLO_5x5")
                .setRankInformations(List.of(expectedRankInfoSubdoc));

        SeasonOrSetRanksSubdocument expectedSeasonSubdoc = new SeasonOrSetRanksSubdocument()
                .setSeasonOrSetNumber(16)
                .setQueueRanks(List.of(expectedQueueRanksSubdoc));

        RanksDocument expectedDocument = new RanksDocument()
                .setPuuid(puuid)
                .setSeasonOrSetRanks(List.of(expectedSeasonSubdoc));

        // Act
        rankMongoAdapter.saveRanks(List.of(seasonRanks), puuid);

        // Assert
        verify(repository).save(expectedDocument);
    }
}