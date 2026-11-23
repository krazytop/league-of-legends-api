package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.InvalidSearchCriteriaException;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRole;
import com.krazytop.leagueoflegends.domain.model.match.FailedMatch;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;
import com.krazytop.leagueoflegends.domain.port.out.match.FailedMatchRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.match.MatchRepositoryPort;
import com.krazytop.leagueoflegends.domain.port.out.match.MatchRiotPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static com.krazytop.leagueoflegends.cucumber.config.CucumberTimeConfig.INSTANT_ACTUEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRiotPort riotPort;

    @Mock
    private MatchRepositoryPort matchRepositoryPort;

    @Mock
    private FailedMatchRepositoryPort failedMatchRepositoryPort;

    @Mock
    private SummonerService summonerService;

    @Mock
    private ArenaCompletionService arenaCompletionService;

    @Spy
    private Clock clock = Clock.fixed(INSTANT_ACTUEL, ZoneOffset.UTC);

    @InjectMocks
    private MatchService matchService;

    @Test
    void searchMatches() {
        // Arrange
        MatchSearchCriteria criteria = new MatchSearchCriteria()
                .setPuuid("puuid-1234")
                .setRole(EnumRole.JUNGLE);
        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<MatchSearchCriteria>()
                .setSearchCriteria(criteria);
        PageResult<Match> expectedResult = new PageResult<>();
        when(matchRepositoryPort.findMatches(pageRequest)).thenReturn(expectedResult);

        // Act
        PageResult<Match> result = matchService.searchMatches(pageRequest);

        // Assert
        assertThat(result).isSameAs(expectedResult);
        verify(matchRepositoryPort).findMatches(pageRequest);
    }

    @Test
    void searchMatches_nullCriteria() {
        // Arrange
        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<>();
        PageResult<Match> expectedResult = new PageResult<>();
        when(matchRepositoryPort.findMatches(pageRequest)).thenReturn(expectedResult);

        // Act
        PageResult<Match> result = matchService.searchMatches(pageRequest);

        // Assert
        assertThat(result).isSameAs(expectedResult);
        verify(matchRepositoryPort).findMatches(pageRequest);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t\n"})
    void searchMatches_roleWithoutPuuid(String blankPuuid) {
        // Arrange
        MatchSearchCriteria criteria = new MatchSearchCriteria()
                .setPuuid(blankPuuid)
                .setRole(EnumRole.MIDDLE);
        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<MatchSearchCriteria>()
                .setSearchCriteria(criteria);

        // Act & Assert
        assertThatThrownBy(() -> matchService.searchMatches(pageRequest))
                .isInstanceOf(InvalidSearchCriteriaException.class)
                .hasMessage("Le filtre 'role' nécessite de renseigner le 'puuid' de l'invocateur");
        verifyNoInteractions(matchRepositoryPort);
    }

    @Test
    void updateMatches_newMatchFromRiot() {
        // Arrange
        String puuid = "puuid-1234";
        Summoner summoner = spy(new Summoner().setPuuid(puuid).setRegion(EnumRegion.EUW1));
        Set<String> completedArenaChampions = new HashSet<>(Set.of("121"));

        Match fetchedMatch = mock(Match.class);
        when(fetchedMatch.setOwners(Set.of(puuid))).thenReturn(fetchedMatch);
        when(fetchedMatch.getArenaWonChampionFor(puuid)).thenReturn(Optional.of("18"));

        when(summonerService.getSummoner(puuid)).thenReturn(summoner);
        when(arenaCompletionService.getArenaCompletion(puuid)).thenReturn(completedArenaChampions);
        when(riotPort.getMatchIds(EnumRegion.EUW1, puuid, 0, 100)).thenReturn(List.of("EUW1_1"));
        when(matchRepositoryPort.findExistingMatchIdsByOwner(List.of("EUW1_1"), puuid)).thenReturn(Set.of());
        when(failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(List.of("EUW1_1"), puuid)).thenReturn(Set.of());
        when(matchRepositoryPort.addOwnerIfMatchExists("EUW1_1", puuid)).thenReturn(Optional.empty());
        when(failedMatchRepositoryPort.addOwnerIfFailedMatchExists("EUW1_1", puuid)).thenReturn(false);
        when(riotPort.getMatch(EnumRegion.EUW1, "EUW1_1")).thenReturn(fetchedMatch);

        // Act
        matchService.updateMatches(puuid, false);

        // Assert
        verify(matchRepositoryPort).saveMatch(fetchedMatch);
        verify(summoner).updateWith(fetchedMatch);
        assertThat(completedArenaChampions).containsExactlyInAnyOrder("121", "18");
        verify(summonerService).saveSummoner(summoner);
        verify(arenaCompletionService).saveArenaCompletion(completedArenaChampions, puuid);
    }

    @Test
    void updateMatches_existingMatchAndExistingFailedMatchInDb() {
        // Arrange
        String puuid = "puuid-1234";
        Summoner summoner = spy(new Summoner().setPuuid(puuid).setRegion(EnumRegion.EUW1));
        Set<String> completedArenaChampions = new HashSet<>();

        Match existingMatch = mock(Match.class);
        when(existingMatch.getArenaWonChampionFor(puuid)).thenReturn(Optional.empty());

        List<String> batchIds = List.of("EUW1_1", "EUW1_2");
        when(summonerService.getSummoner(puuid)).thenReturn(summoner);
        when(arenaCompletionService.getArenaCompletion(puuid)).thenReturn(completedArenaChampions);
        when(riotPort.getMatchIds(EnumRegion.EUW1, puuid, 0, 100)).thenReturn(batchIds);
        when(matchRepositoryPort.findExistingMatchIdsByOwner(batchIds, puuid)).thenReturn(Set.of());
        when(failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(batchIds, puuid)).thenReturn(Set.of());
        when(matchRepositoryPort.addOwnerIfMatchExists("EUW1_1", puuid)).thenReturn(Optional.of(existingMatch));
        when(matchRepositoryPort.addOwnerIfMatchExists("EUW1_2", puuid)).thenReturn(Optional.empty());
        when(failedMatchRepositoryPort.addOwnerIfFailedMatchExists("EUW1_2", puuid)).thenReturn(true);

        // Act
        matchService.updateMatches(puuid, false);

        // Assert
        verify(summoner).updateWith(existingMatch);
        verify(riotPort, never()).getMatch(any(), any());
        verify(matchRepositoryPort, never()).saveMatch(any());
        verify(failedMatchRepositoryPort, never()).saveFailedMatch(any());
        verify(summonerService).saveSummoner(summoner);
        verify(arenaCompletionService).saveArenaCompletion(completedArenaChampions, puuid);
    }

    @Test
    void updateMatches_riotErrorSavesFailedMatch() {
        // Arrange
        String puuid = "puuid-1234";
        Summoner summoner = spy(new Summoner().setPuuid(puuid).setRegion(EnumRegion.EUW1));
        Set<String> completedArenaChampions = new HashSet<>();

        when(summonerService.getSummoner(puuid)).thenReturn(summoner);
        when(arenaCompletionService.getArenaCompletion(puuid)).thenReturn(completedArenaChampions);
        when(riotPort.getMatchIds(EnumRegion.EUW1, puuid, 0, 100)).thenReturn(List.of("EUW1_ERR"));
        when(matchRepositoryPort.findExistingMatchIdsByOwner(List.of("EUW1_ERR"), puuid)).thenReturn(Set.of());
        when(failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(List.of("EUW1_ERR"), puuid)).thenReturn(Set.of());
        when(matchRepositoryPort.addOwnerIfMatchExists("EUW1_ERR", puuid)).thenReturn(Optional.empty());
        when(failedMatchRepositoryPort.addOwnerIfFailedMatchExists("EUW1_ERR", puuid)).thenReturn(false);
        when(riotPort.getMatch(EnumRegion.EUW1, "EUW1_ERR")).thenThrow(new RuntimeException("Riot API 500"));

        FailedMatch expectedFailedMatch = new FailedMatch()
                .setId("EUW1_ERR")
                .setRegion(EnumRegion.EUW1)
                .setOwners(Set.of(puuid))
                .setErrorType("RuntimeException")
                .setErrorMessage("Riot API 500")
                .setCreatedAt(INSTANT_ACTUEL)
                .setLastAttemptAt(INSTANT_ACTUEL);

        // Act
        matchService.updateMatches(puuid, false);

        // Assert
        verify(failedMatchRepositoryPort).saveFailedMatch(expectedFailedMatch);
        verify(summoner, never()).updateWith(any(Match.class));
        verify(summonerService).saveSummoner(summoner);
        verify(arenaCompletionService).saveArenaCompletion(completedArenaChampions, puuid);
    }

    @Test
    void updateMatches_stopsWhenAlreadyOwnedMatchEncounteredWithoutForceFullSync() {
        // Arrange
        String puuid = "puuid-1234";
        Summoner summoner = new Summoner().setPuuid(puuid).setRegion(EnumRegion.EUW1);
        Set<String> completedArenaChampions = new HashSet<>();

        List<String> batchIds = List.of("EUW1_NEW", "EUW1_ALREADY_OWNED", "EUW1_IGNORED");
        Match newMatch = mock(Match.class);
        when(newMatch.getArenaWonChampionFor(puuid)).thenReturn(Optional.empty());

        when(summonerService.getSummoner(puuid)).thenReturn(summoner);
        when(arenaCompletionService.getArenaCompletion(puuid)).thenReturn(completedArenaChampions);
        when(riotPort.getMatchIds(EnumRegion.EUW1, puuid, 0, 100)).thenReturn(batchIds);
        when(matchRepositoryPort.findExistingMatchIdsByOwner(batchIds, puuid)).thenReturn(Set.of("EUW1_ALREADY_OWNED"));
        when(failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(batchIds, puuid)).thenReturn(Set.of());
        when(matchRepositoryPort.addOwnerIfMatchExists("EUW1_NEW", puuid)).thenReturn(Optional.of(newMatch));

        // Act
        matchService.updateMatches(puuid, false);

        // Assert
        verify(matchRepositoryPort).addOwnerIfMatchExists("EUW1_NEW", puuid);
        verify(matchRepositoryPort, never()).addOwnerIfMatchExists("EUW1_ALREADY_OWNED", puuid);
        verify(matchRepositoryPort, never()).addOwnerIfMatchExists("EUW1_IGNORED", puuid);
    }

    @Test
    void updateMatches_continuesPaginationWithForceFullSync() {
        // Arrange
        String puuid = "puuid-1234";
        Summoner summoner = new Summoner().setPuuid(puuid).setRegion(EnumRegion.EUW1);
        Set<String> completedArenaChampions = new HashSet<>();

        List<String> firstBatch = IntStream.range(0, 100)
                .mapToObj(i -> "EUW1_" + i)
                .toList();
        List<String> secondBatch = List.of("EUW1_100");

        Match match100 = mock(Match.class);
        when(match100.getArenaWonChampionFor(puuid)).thenReturn(Optional.empty());

        when(summonerService.getSummoner(puuid)).thenReturn(summoner);
        when(arenaCompletionService.getArenaCompletion(puuid)).thenReturn(completedArenaChampions);
        when(riotPort.getMatchIds(EnumRegion.EUW1, puuid, 0, 100)).thenReturn(firstBatch);
        when(riotPort.getMatchIds(EnumRegion.EUW1, puuid, 100, 100)).thenReturn(secondBatch);
        when(matchRepositoryPort.findExistingMatchIdsByOwner(firstBatch, puuid)).thenReturn(new HashSet<>(firstBatch));
        when(failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(firstBatch, puuid)).thenReturn(Set.of());
        when(matchRepositoryPort.findExistingMatchIdsByOwner(secondBatch, puuid)).thenReturn(Set.of());
        when(failedMatchRepositoryPort.findExistingFailedMatchIdsByOwner(secondBatch, puuid)).thenReturn(Set.of());
        when(matchRepositoryPort.addOwnerIfMatchExists("EUW1_100", puuid)).thenReturn(Optional.of(match100));

        // Act
        matchService.updateMatches(puuid, true);

        // Assert
        verify(riotPort).getMatchIds(EnumRegion.EUW1, puuid, 0, 100);
        verify(riotPort).getMatchIds(EnumRegion.EUW1, puuid, 100, 100);
        verify(matchRepositoryPort, times(1)).addOwnerIfMatchExists(any(), eq(puuid));
        verify(matchRepositoryPort).addOwnerIfMatchExists("EUW1_100", puuid);
    }
}