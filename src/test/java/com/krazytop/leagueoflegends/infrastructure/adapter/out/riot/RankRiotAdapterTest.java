package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.RankInformations;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.RankRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankRiotAdapterTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private R4J r4j;

    @Spy
    private ShardRiotMapper shardMapper = Mappers.getMapper(ShardRiotMapper.class);

    @Spy
    private RankRiotMapper rankMapper = Mappers.getMapper(RankRiotMapper.class);

    @InjectMocks
    private RankRiotAdapter rankRiotAdapter;

    @Test
    void fetchRanks() {
        // Arrange
        String puuid = "test-puuid-123";

        LeagueEntry leagueEntry = new LeagueEntry();
        ReflectionTestUtils.setField(leagueEntry, "queueType", GameQueueType.RANKED_SOLO_5X5);
        ReflectionTestUtils.setField(leagueEntry, "tier", "DIAMOND");
        ReflectionTestUtils.setField(leagueEntry, "rank", "II");
        ReflectionTestUtils.setField(leagueEntry, "leaguePoints", 75);
        ReflectionTestUtils.setField(leagueEntry, "wins", 120);
        ReflectionTestUtils.setField(leagueEntry, "losses", 105);

        RankInformations expectedRankInfo = new RankInformations()
                .setTier("DIAMOND")
                .setRank("II")
                .setLeaguePoints(75)
                .setWins(120)
                .setLosses(105);

        QueueRanks expectedQueueRanks = new QueueRanks()
                .setName("RANKED_SOLO_5x5")
                .setRankInformations(List.of(expectedRankInfo));

        when(r4j.getLoLAPI()
                .getLeagueAPI()
                .getLeagueEntriesByPUUID(LeagueShard.EUW1, puuid))
                .thenReturn(List.of(leagueEntry));

        // Act
        List<QueueRanks> result = rankRiotAdapter.fetchRanks(EnumRegion.EUW1, puuid);

        // Assert
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("rankInformations.date")
                .isEqualTo(List.of(expectedQueueRanks));
    }
}