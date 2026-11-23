package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.model.Account;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.port.out.account.AccountRiotPort;
import com.krazytop.leagueoflegends.domain.port.out.summoner.SummonerRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.summoner.SummonerRiotPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SummonerServiceTest {

    private static final Instant FIXED_INSTANT = Instant.parse("2026-11-17T12:00:00Z");

    @Mock
    private AccountRiotPort accountRiotPort;

    @Mock
    private SummonerRiotPort summonerRiotPort;

    @Mock
    private SummonerRepositoryPort repositoryPort;

    private SummonerService summonerService;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
        summonerService = new SummonerService(accountRiotPort, summonerRiotPort, repositoryPort, fixedClock);
    }

    @Test
    void getSummoner_byPuuid_whenFoundInRepository_returnsFromRepository() {
        // Arrange
        String puuid = "test-puuid-123";
        Summoner storedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567");

        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.of(storedSummoner));

        // Act
        Summoner result = summonerService.getOrFetchSummoner(puuid);

        // Assert
        assertThat(result).isEqualTo(storedSummoner);
        verify(repositoryPort).findSummoner(puuid);
        verifyNoInteractions(accountRiotPort, summonerRiotPort);
    }

    @Test
    void getSummoner_byPuuid_whenNotInRepository_fetchesFromRiot() {
        // Arrange
        String puuid = "test-puuid-123";
        Account account = new Account()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1);

        Summoner riotSummoner = new Summoner()
                .setPuuid(puuid)
                .setLevel(450)
                .setIcon("4567");

        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.empty());
        when(accountRiotPort.fetchAccount(puuid)).thenReturn(Optional.of(account));
        when(summonerRiotPort.fetchSummoner(EnumRegion.EUW1, puuid)).thenReturn(Optional.of(riotSummoner));

        Summoner expectedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567");

        // Act
        Summoner result = summonerService.getOrFetchSummoner(puuid);

        // Assert
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedSummoner);
    }

    @Test
    void getSummoner_byPuuid_whenAccountNotFoundOnRiot_throwsResourceNotFoundException() {
        // Arrange
        String puuid = "unknown-puuid";
        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.empty());
        when(accountRiotPort.fetchAccount(puuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> summonerService.getOrFetchSummoner(puuid))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("L'invocateur avec le puuid unknown-puuid n'existe pas");
        verifyNoInteractions(summonerRiotPort);
    }

    @Test
    void getSummoner_byPuuid_whenSummonerNotFoundOnRiot_throwsResourceNotFoundException() {
        // Arrange
        String puuid = "valorant-only-puuid";
        Account account = new Account()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1);

        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.empty());
        when(accountRiotPort.fetchAccount(puuid)).thenReturn(Optional.of(account));
        when(summonerRiotPort.fetchSummoner(EnumRegion.EUW1, puuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> summonerService.getOrFetchSummoner(puuid))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("L'invocateur avec le puuid valorant-only-puuid n'existe pas");
    }

    @Test
    void getSummoner_byTagAndName_whenFoundInRepository_returnsFromRepository() {
        // Arrange
        String tag = "EUW";
        String name = "Krazytop";
        Summoner storedSummoner = new Summoner()
                .setPuuid("test-puuid-123")
                .setName(name)
                .setTag(tag)
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567");

        when(repositoryPort.findSummoner(tag, name)).thenReturn(Optional.of(storedSummoner));

        // Act
        Summoner result = summonerService.getOrFetchSummoner(tag, name);

        // Assert
        assertThat(result).isEqualTo(storedSummoner);
        verify(repositoryPort).findSummoner(tag, name);
        verifyNoInteractions(accountRiotPort, summonerRiotPort);
    }

    @Test
    void getSummoner_byTagAndName_whenNotInRepository_fetchesFromRiot() {
        // Arrange
        String puuid = "test-puuid-123";
        String tag = "EUW";
        String name = "Krazytop";
        Account account = new Account()
                .setPuuid(puuid)
                .setName(name)
                .setTag(tag)
                .setRegion(EnumRegion.EUW1);

        Summoner riotSummoner = new Summoner()
                .setPuuid(puuid)
                .setLevel(450)
                .setIcon("4567");

        when(repositoryPort.findSummoner(tag, name)).thenReturn(Optional.empty());
        when(accountRiotPort.fetchAccount(name, tag)).thenReturn(Optional.of(account));
        when(summonerRiotPort.fetchSummoner(EnumRegion.EUW1, puuid)).thenReturn(Optional.of(riotSummoner));

        Summoner expectedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName(name)
                .setTag(tag)
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567");

        // Act
        Summoner result = summonerService.getOrFetchSummoner(tag, name);

        // Assert
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedSummoner);
    }

    @Test
    void getSummoner_byTagAndName_whenNotFoundOnRiot_throwsResourceNotFoundException() {
        // Arrange
        String tag = "EUW";
        String name = "Unknown";
        when(repositoryPort.findSummoner(tag, name)).thenReturn(Optional.empty());
        when(accountRiotPort.fetchAccount(name, tag)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> summonerService.getOrFetchSummoner(tag, name))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("L'invocateur Unknown #EUW n'existe pas");
        verifyNoInteractions(summonerRiotPort);
    }

    @Test
    void getSummoner_shouldReturnSummoner() throws ResourceNotFoundException {
        // Arrange
        String puuid = "test-puuid";
        Summoner summoner = new Summoner()
                .setPuuid(puuid);

        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.of(summoner));

        // Act
        Summoner result = summonerService.getSummoner(puuid);

        // Assert
        assertThat(result).isEqualTo(summoner);
        verify(repositoryPort).findSummoner(puuid);
    }

    @Test
    void getSummoner_shouldThrowResourceNotFoundException_whenSummonerDoesNotExist() {
        // Arrange
        String puuid = "unknown-puuid";

        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> summonerService.getSummoner(puuid))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("L'invocateur doit être importé avant de faire cette action");

        verify(repositoryPort).findSummoner(puuid);
    }

    @Test
    void getSummonerRegion_shouldReturnRegion() throws ResourceNotFoundException {
        // Arrange
        String puuid = "test-puuid";
        Summoner summoner = new Summoner()
                .setPuuid(puuid)
                .setRegion(EnumRegion.EUW1);

        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.of(summoner));

        // Act
        EnumRegion result = summonerService.getSummonerRegion(puuid);

        // Assert
        assertThat(result).isEqualTo(EnumRegion.EUW1);
        verify(repositoryPort).findSummoner(puuid);
    }

    @Test
    void updateSummoner_whenFirstImport_savesNewSummonerWithUpdateDate() {
        // Arrange
        String puuid = "test-puuid-123";
        Account account = new Account()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1);

        Summoner riotSummoner = new Summoner()
                .setPuuid(puuid)
                .setLevel(450)
                .setIcon("4567");

        when(accountRiotPort.fetchAccount(puuid)).thenReturn(Optional.of(account));
        when(summonerRiotPort.fetchSummoner(EnumRegion.EUW1, puuid)).thenReturn(Optional.of(riotSummoner));
        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.empty());
        when(repositoryPort.saveSummoner(any(Summoner.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Summoner expectedSavedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567")
                .setUpdateDate(FIXED_INSTANT);

        // Act
        Summoner result = summonerService.updateSummoner(puuid);

        // Assert
        ArgumentCaptor<Summoner> captor = ArgumentCaptor.captor();
        verify(repositoryPort).saveSummoner(captor.capture());
        assertThat(captor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedSavedSummoner);
        assertThat(result).isEqualTo(captor.getValue());
    }

    @Test
    void updateSummoner_whenSummonerAlreadyExists_preservesSpentTimeAndPlayedSeasons() {
        // Arrange
        String puuid = "test-puuid-123";
        Account updatedAccount = new Account()
                .setPuuid(puuid)
                .setName("NewKrazytop")
                .setTag("FR1")
                .setRegion(EnumRegion.EUW1);

        Summoner riotSummoner = new Summoner()
                .setPuuid(puuid)
                .setLevel(452)
                .setIcon("9999");

        Summoner existingSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1)
                .setLevel(450)
                .setIcon("4567")
                .setUpdateDate(Instant.parse("2026-10-01T10:00:00Z"))
                .setSpentTime(123456L)
                .setPlayedSeasons(Set.of(13, 14, 15));

        when(accountRiotPort.fetchAccount(puuid)).thenReturn(Optional.of(updatedAccount));
        when(summonerRiotPort.fetchSummoner(EnumRegion.EUW1, puuid)).thenReturn(Optional.of(riotSummoner));
        when(repositoryPort.findSummoner(puuid)).thenReturn(Optional.of(existingSummoner));
        when(repositoryPort.saveSummoner(any(Summoner.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Summoner expectedSavedSummoner = new Summoner()
                .setPuuid(puuid)
                .setName("NewKrazytop")
                .setTag("FR1")
                .setRegion(EnumRegion.EUW1)
                .setLevel(452)
                .setIcon("9999")
                .setUpdateDate(FIXED_INSTANT)
                .setSpentTime(123456L)
                .setPlayedSeasons(Set.of(13, 14, 15));

        // Act
        Summoner result = summonerService.updateSummoner(puuid);

        // Assert
        ArgumentCaptor<Summoner> captor = ArgumentCaptor.captor();
        verify(repositoryPort).saveSummoner(captor.capture());
        assertThat(captor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedSavedSummoner);
        assertThat(result).isEqualTo(captor.getValue());
    }

    @Test
    void saveSummoner() {
        // Arrange
        Summoner summoner = new Summoner().setPuuid("test-puuid");

        when(repositoryPort.saveSummoner(summoner)).thenReturn(summoner);

        // Act
        Summoner result = summonerService.saveSummoner(summoner);

        // Then
        assertThat(result).isEqualTo(summoner);
        verify(repositoryPort).saveSummoner(summoner);
    }
}