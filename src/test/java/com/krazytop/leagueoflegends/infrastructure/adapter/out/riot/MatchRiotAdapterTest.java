package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.match.Participant;
import com.krazytop.leagueoflegends.domain.model.match.RunesStats;
import com.krazytop.leagueoflegends.domain.model.match.Team;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.common.QueueMappingHelper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.DateRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.MatchRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.MatchRiotMapperImpl;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.utils.Utils;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.pojo.shared.GAMHSMatch;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchRiotAdapterTest {

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private R4J r4j;

    @Spy
    private ShardRiotMapper shardMapper = Mappers.getMapper(ShardRiotMapper.class);

    @Spy
    private MatchRiotMapper matchMapper = new MatchRiotMapperImpl(Mappers.getMapper(DateRiotMapper.class));

    @InjectMocks
    private MatchRiotAdapter adapter;

    @Test
    void getMatchIds_shouldReturnMatchIdsFromRiotApi() {
        String puuid = "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw";
        List<String> expectedIds = List.of("EUW1_7941319192", "EUW1_7939943973");

        when(r4j.getLoLAPI().getMatchAPI()
                .getMatchList(RegionShard.EUROPE, puuid, null, null, 0, 100, null, null))
                .thenReturn(expectedIds);

        List<String> result = adapter.getMatchIds(EnumRegion.EUW1, puuid, 0, 100);

        assertThat(result).containsExactlyElementsOf(expectedIds);
    }

    @Test
    void getMatch_shouldMapClassicMatchProperly() throws IOException {
        String matchId = "EUW1_7939943973";
        GAMHSMatch rawMatch = loadRawMatchFromResource("/riot/match-normal.json");

        try (MockedConstruction<MatchBuilder> ignored = mockConstruction(MatchBuilder.class, (mock, context) -> {
            when(mock.withContext(any())).thenReturn(mock);
            when(mock.getMatchRAW()).thenReturn(rawMatch);
        })) {
            Match result = adapter.getMatch(EnumRegion.EUW1, matchId);

            assertThat(result)
                    .extracting(Match::getId, Match::getVersion, Match::getSeason, Match::getDatetime,
                            Match::getDuration, Match::getQueue, Match::isRemake)
                    .containsExactly("EUW1_7939943973", "16.15", 16, Instant.parse("2026-08-04T13:28:32.620Z"),
                            2268L, QueueMappingHelper.toQueue(420), false);
            assertThat(result.getOwners()).isNullOrEmpty();

            // 2 équipes classiques (100 et 200)
            assertThat(result.getTeams()).hasSize(2);

            Team blueTeam = result.getTeams().getFirst();
            assertThat(blueTeam.getId()).isEqualTo("100");
            assertThat(blueTeam.isHasWin()).isFalse();
            assertThat(blueTeam.getPlacement()).isNull();
            assertThat(blueTeam.getBans()).containsExactly("147", "74", "18", "24", "233");
            assertThat(blueTeam.getObjectives())
                    .containsEntry("baron", 1)
                    .containsEntry("champion", 51)
                    .containsEntry("dragon", 2)
                    .containsEntry("horde", 3)
                    .containsEntry("inhibitor", 0)
                    .containsEntry("riftHerald", 1)
                    .containsEntry("tower", 5)
                    .containsEntry("atakhan", 0);
            assertThat(blueTeam.getParticipants()).hasSize(5);

            // Participant 1 : Darius
            Participant darius = blueTeam.getParticipants().getFirst();
            assertThat(darius)
                    .extracting(Participant::getChampion, Participant::getChampLevel, Participant::getKills,
                            Participant::getAssists, Participant::getDeaths, Participant::getRole,
                            Participant::getVisionScore, Participant::getMinions, Participant::getNeutralMinions,
                            Participant::getDoubleKills, Participant::getPhysicalDamageDealtToChampions,
                            Participant::getMagicDamageDealtToChampions, Participant::getTrueDamageDealtToChampions,
                            Participant::getPhysicalDamageTaken, Participant::getMagicDamageTaken,
                            Participant::getTrueDamageTaken, Participant::getGolds,
                            Participant::getGameEndedInEarlySurrender, Participant::getItem0,
                            Participant::getItem5, Participant::getWard, Participant::getSummonerSpell1,
                            Participant::getSummonerSpell2, Participant::getAugment1)
                    .containsExactly("122", 20, 12, 7, 9, "TOP", 23, 238, 4, 2, 45893, 0, 17594,
                            32511, 29405, 6431, 16241, false, "3143", "3742", "3340", "4", "14", null);

            assertThat(darius.getSummoner())
                    .extracting(Summoner::getPuuid, Summoner::getName, Summoner::getTag, Summoner::getLevel, Summoner::getIcon)
                    .containsExactly("30EQ2MYfgZvzSSGUaxwWd6mcz47wt4ZRcYl8SINmrsRnQblLVsomDlQzF7pmnN7TA43h4p3UO6KNCw",
                            "Beewer", "EUW", 548, "4748");

            assertThat(darius.getRunes().getStats())
                    .extracting(RunesStats::getDefense, RunesStats::getFlex, RunesStats::getOffense)
                    .containsExactly("5001", "5008", "5005");
            assertThat(darius.getRunes().getRuneCategories()).hasSize(2);
            assertThat(darius.getRunes().getRuneCategories().getFirst().getStyle()).isEqualTo("8000");
            assertThat(darius.getRunes().getRuneCategories().getFirst().getPerks())
                    .containsExactly("8010", "9111", "9104", "8299");

            // Participant 4 : Aphelios (vérifie item5 == 0 -> null)
            Participant aphelios = blueTeam.getParticipants().get(3);
            assertThat(aphelios.getItem4()).isEqualTo("3046");
            assertThat(aphelios.getItem5()).isNull();
        }
    }

    @Test
    void getMatch_shouldMapArenaMatchProperly() throws IOException {
        String matchId = "EUW1_7941319192";
        GAMHSMatch rawMatch = loadRawMatchFromResource("/riot/match-arena.json");

        try (MockedConstruction<MatchBuilder> ignored = mockConstruction(MatchBuilder.class, (mock, context) -> {
            when(mock.withContext(any())).thenReturn(mock);
            when(mock.getMatchRAW()).thenReturn(rawMatch);
        })) {
            Match result = adapter.getMatch(EnumRegion.EUW1, matchId);

            assertThat(result)
                    .extracting(Match::getId, Match::getVersion, Match::getSeason, Match::getDatetime,
                            Match::getDuration, Match::getQueue, Match::isRemake)
                    .containsExactly("EUW1_7941319192", "16.15", 16, Instant.parse("2026-08-05T18:30:37.751Z"),
                            1411L, QueueMappingHelper.toQueue(1750), false);
            assertThat(result.getOwners()).isNullOrEmpty();

            // 6 équipes de 3 invocateurs triées par placement (1 -> 6)
            assertThat(result.getTeams()).hasSize(6);
            assertThat(result.getTeams())
                    .extracting(Team::getId, Team::getPlacement, Team::isHasWin)
                    .containsExactly(
                            Tuple.tuple("5", 1, true),
                            Tuple.tuple("1", 2, true),
                            Tuple.tuple("4", 3, true),
                            Tuple.tuple("2", 4, false),
                            Tuple.tuple("6", 5, false),
                            Tuple.tuple("3", 6, false)
                    );

            // Team 5 (placement 1) : Braum, Draven, Seraphine
            Team firstTeam = result.getTeams().getFirst();
            assertThat(firstTeam.getObjectives()).isNull();
            assertThat(firstTeam.getBans()).isNullOrEmpty();
            assertThat(firstTeam.getParticipants()).hasSize(3);

            Participant braum = firstTeam.getParticipants().getFirst();
            assertThat(braum)
                    .extracting(Participant::getChampion, Participant::getRole,
                            p -> p.getSummoner().getName(), p -> p.getSummoner().getTag(),
                            Participant::getAugment1, Participant::getAugment2,
                            Participant::getAugment3, Participant::getAugment4)
                    .containsExactly("201", "INVALID", "a 200 dans lmur", "dead", "193", "216", "219", null);

            Participant draven = firstTeam.getParticipants().get(1);
            assertThat(draven)
                    .extracting(Participant::getChampion, Participant::getItem0, Participant::getItem1,
                            Participant::getItem2, Participant::getAugment1, Participant::getAugment5, Participant::getAugment6)
                    .containsExactly("119", "220012", "447101", null, "220", "200", null);

            // Team 4 (placement 3) : Twisted Fate possède 6 augments
            Team thirdTeam = result.getTeams().get(2);
            Participant twistedFate = thirdTeam.getParticipants().get(1);
            assertThat(twistedFate.getChampion()).isEqualTo("4");
            assertThat(twistedFate.getAugment1()).isEqualTo("44");
            assertThat(twistedFate.getAugment6()).isEqualTo("45");

            assertThat(result.getTeams())
                    .flatExtracting(Team::getParticipants)
                    .extracting(Participant::getRunes)
                    .containsOnlyNulls();
        }
    }

    private GAMHSMatch loadRawMatchFromResource(String path) throws IOException {
        try (var is = Objects.requireNonNull(getClass().getResourceAsStream(path), "Missing test resource: " + path)) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return Utils.getGson().fromJson(json, GAMHSMatch.class);
        }
    }
}