package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.Account;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.AccountRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.api.regions.ShardableGame;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import no.stelar7.api.r4j.pojo.shared.RiotAccountRegion;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountRiotAdapterTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private R4J r4j;

    @Spy
    private ShardRiotMapper shardMapper = Mappers.getMapper(ShardRiotMapper.class);

    @Spy
    private AccountRiotMapper accountMapper = Mappers.getMapper(AccountRiotMapper.class);

    @InjectMocks
    private AccountRiotAdapter accountRiotAdapter;

    @Test
    void fetchAccount_byPuuid() {
        // Arrange
        String puuid = "test-puuid-123";

        RiotAccount riotAccount = new RiotAccount();
        ReflectionTestUtils.setField(riotAccount, "puuid", puuid);
        ReflectionTestUtils.setField(riotAccount, "gameName", "Krazytop");
        ReflectionTestUtils.setField(riotAccount, "tagLine", "EUW");

        RiotAccountRegion riotAccountRegion = new RiotAccountRegion();
        ReflectionTestUtils.setField(riotAccountRegion, "puuid", puuid);
        ReflectionTestUtils.setField(riotAccountRegion, "game", "lol");
        ReflectionTestUtils.setField(riotAccountRegion, "region", "euw1");

        when(r4j.getAccountAPI().getAccountByPUUID(RegionShard.EUROPE, puuid)).thenReturn(riotAccount);
        when(r4j.getAccountAPI().getActiveRegion(RegionShard.EUROPE, ShardableGame.LOL, puuid)).thenReturn(riotAccountRegion);

        Account expectedAccount = new Account()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1);

        // Act
        Optional<Account> result = accountRiotAdapter.fetchAccount(puuid);

        // Assert
        assertThat(result).contains(expectedAccount);
        verify(r4j.getAccountAPI()).getAccountByPUUID(RegionShard.EUROPE, puuid);
        verify(r4j.getAccountAPI()).getActiveRegion(RegionShard.EUROPE, ShardableGame.LOL, puuid);
    }

    @Test
    void fetchAccount_byNameAndTag() {
        // Arrange
        String puuid = "test-puuid-123";
        String name = "Krazytop";
        String tag = "EUW";

        RiotAccount riotAccount = new RiotAccount();
        ReflectionTestUtils.setField(riotAccount, "puuid", puuid);
        ReflectionTestUtils.setField(riotAccount, "gameName", name);
        ReflectionTestUtils.setField(riotAccount, "tagLine", tag);

        RiotAccountRegion riotAccountRegion = new RiotAccountRegion();
        ReflectionTestUtils.setField(riotAccountRegion, "puuid", puuid);
        ReflectionTestUtils.setField(riotAccountRegion, "game", "lol");
        ReflectionTestUtils.setField(riotAccountRegion, "region", "euw1");

        when(r4j.getAccountAPI().getAccountByTag(RegionShard.EUROPE, name, tag)).thenReturn(riotAccount);
        when(r4j.getAccountAPI().getActiveRegion(RegionShard.EUROPE, ShardableGame.LOL, puuid)).thenReturn(riotAccountRegion);

        Account expectedAccount = new Account()
                .setPuuid(puuid)
                .setName(name)
                .setTag(tag)
                .setRegion(EnumRegion.EUW1);

        // Act
        Optional<Account> result = accountRiotAdapter.fetchAccount(name, tag);

        // Assert
        assertThat(result).contains(expectedAccount);
        verify(r4j.getAccountAPI()).getAccountByTag(RegionShard.EUROPE, name, tag);
        verify(r4j.getAccountAPI()).getActiveRegion(RegionShard.EUROPE, ShardableGame.LOL, puuid);
    }

    @Test
    void fetchAccount_byNameAndTag_whenAccountNotFound_returnsEmpty() {
        // Arrange
        String name = "Unknown";
        String tag = "EUW";
        when(r4j.getAccountAPI().getAccountByTag(RegionShard.EUROPE, name, tag)).thenReturn(null);

        // Act
        Optional<Account> result = accountRiotAdapter.fetchAccount(name, tag);

        // Assert
        assertThat(result).isEmpty();
        verify(r4j.getAccountAPI()).getAccountByTag(RegionShard.EUROPE, name, tag);
        verifyNoMoreInteractions(r4j.getAccountAPI());
    }
}