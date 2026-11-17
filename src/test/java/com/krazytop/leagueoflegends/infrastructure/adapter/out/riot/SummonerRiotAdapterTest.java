package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.SummonerRiotMapper;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummonerRiotAdapterTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private R4J r4j;

    @Spy
    private ShardRiotMapper shardMapper = Mappers.getMapper(ShardRiotMapper.class);

    @Spy
    private SummonerRiotMapper summonerMapper = Mappers.getMapper(SummonerRiotMapper.class);

    @InjectMocks
    private SummonerRiotAdapter summonerRiotAdapter;

    @Test
    void fetchSummoner() {
        // Arrange
        String puuid = "test-puuid-123";

        no.stelar7.api.r4j.pojo.lol.summoner.Summoner riotSummoner = new no.stelar7.api.r4j.pojo.lol.summoner.Summoner();
        ReflectionTestUtils.setField(riotSummoner, "summonerLevel", 450);
        ReflectionTestUtils.setField(riotSummoner, "profileIconId", 4567);

        when(r4j.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, puuid)).thenReturn(riotSummoner);

        Summoner expectedSummoner = new Summoner()
                .setLevel(450)
                .setIcon(4567);

        // Act
        Optional<Summoner> result = summonerRiotAdapter.fetchSummoner(EnumRegion.EUW1, puuid);

        // Assert
        assertThat(result).contains(expectedSummoner);
        verify(r4j.getLoLAPI().getSummonerAPI()).getSummonerByPUUID(LeagueShard.EUW1, puuid);
    }

    @Test
    void fetchSummoner_whenSummonerNotFound_returnsEmpty() {
        // Arrange
        String puuid = "unknown-puuid";
        when(r4j.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, puuid)).thenReturn(null);

        // Act
        Optional<Summoner> result = summonerRiotAdapter.fetchSummoner(EnumRegion.EUW1, puuid);

        // Assert
        assertThat(result).isEmpty();
        verify(r4j.getLoLAPI().getSummonerAPI()).getSummonerByPUUID(LeagueShard.EUW1, puuid);
    }
}