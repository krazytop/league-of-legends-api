package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.DateRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.MasteryRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.MasteryRiotMapperImpl;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MasteryRiotAdapterTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private R4J r4j;

    @Spy
    private ShardRiotMapper shardMapper = Mappers.getMapper(ShardRiotMapper.class);

    @Spy
    private MasteryRiotMapper masteryMapper = new MasteryRiotMapperImpl(Mappers.getMapper(DateRiotMapper.class));

    @InjectMocks
    private MasteryRiotAdapter masteryRiotAdapter;

    @Test
    void fetchMasteries() {
        // Arrange
        String puuid = "test-puuid-123";
        Instant lastPlayTime = Instant.parse("2026-10-01T12:00:00Z");

        ChampionMastery r4jMastery = new ChampionMastery();
        ReflectionTestUtils.setField(r4jMastery, "championId", 157);
        ReflectionTestUtils.setField(r4jMastery, "championLevel", 7);
        ReflectionTestUtils.setField(r4jMastery, "championPoints", 125000);
        ReflectionTestUtils.setField(r4jMastery, "lastPlayTime", lastPlayTime.toEpochMilli());

        Mastery expectedMastery = new Mastery()
                .setChampion("157")
                .setLevel(7)
                .setPoints(125000)
                .setLastPlayTime(lastPlayTime);

        when(r4j.getLoLAPI()
                .getMasteryAPI()
                .getChampionMasteries(LeagueShard.EUW1, puuid))
                .thenReturn(List.of(r4jMastery));

        // Act
        List<Mastery> result = masteryRiotAdapter.fetchMasteries(EnumRegion.EUW1, puuid);

        // Assert
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(List.of(expectedMastery));
    }
}