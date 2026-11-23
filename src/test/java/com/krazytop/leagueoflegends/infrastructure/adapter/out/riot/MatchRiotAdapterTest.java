package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.*;
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

            assertThat(result.getId()).isEqualTo("EUW1_7939943973");
            assertThat(result.getVersion()).isEqualTo("16.15");
            assertThat(result.getSeason()).isEqualTo(16);
            assertThat(result.getDatetime()).isEqualTo(Instant.parse("2026-08-04T13:28:32.620Z"));
            assertThat(result.getDuration()).isEqualTo(2268L);
            assertThat(result.getQueue()).isEqualTo(QueueMappingHelper.toQueue(420));
            assertThat(result.isRemake()).isFalse();
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
            assertThat(darius.getChampion()).isEqualTo("122");
            assertThat(darius.getChampLevel()).isEqualTo(20);
            assertThat(darius.getKills()).isEqualTo(12);
            assertThat(darius.getAssists()).isEqualTo(7);
            assertThat(darius.getDeaths()).isEqualTo(9);
            assertThat(darius.getRole()).isEqualTo("TOP");
            assertThat(darius.getVisionScore()).isEqualTo(23);
            assertThat(darius.getMinions()).isEqualTo(238);
            assertThat(darius.getNeutralMinions()).isEqualTo(4);
            assertThat(darius.getDoubleKills()).isEqualTo(2);
            assertThat(darius.getPhysicalDamageDealtToChampions()).isEqualTo(45893);
            assertThat(darius.getMagicDamageDealtToChampions()).isZero();
            assertThat(darius.getTrueDamageDealtToChampions()).isEqualTo(17594);
            assertThat(darius.getPhysicalDamageTaken()).isEqualTo(32511);
            assertThat(darius.getMagicDamageTaken()).isEqualTo(29405);
            assertThat(darius.getTrueDamageTaken()).isEqualTo(6431);
            assertThat(darius.getGolds()).isEqualTo(16241);
            assertThat(darius.getGameEndedInEarlySurrender()).isFalse();
            assertThat(darius.getItem0()).isEqualTo("3143");
            assertThat(darius.getItem5()).isEqualTo("3742");
            assertThat(darius.getWard()).isEqualTo("3340");
            assertThat(darius.getSummonerSpell1()).isEqualTo("4");
            assertThat(darius.getSummonerSpell2()).isEqualTo("14");
            assertThat(darius.getSummoner().getPuuid())
                    .isEqualTo("30EQ2MYfgZvzSSGUaxwWd6mcz47wt4ZRcYl8SINmrsRnQblLVsomDlQzF7pmnN7TA43h4p3UO6KNCw");
            assertThat(darius.getSummoner().getName()).isEqualTo("Beewer");
            assertThat(darius.getSummoner().getTag()).isEqualTo("EUW");
            assertThat(darius.getSummoner().getLevel()).isEqualTo(548);
            assertThat(darius.getSummoner().getIcon()).isEqualTo("4748");
            assertThat(darius.getRunes().getStats())
                    .extracting(RunesStats::getDefense, RunesStats::getFlex, RunesStats::getOffense)
                    .containsExactly("5001", "5008", "5005");
            assertThat(darius.getRunes().getRuneCategories()).hasSize(2);
            assertThat(darius.getRunes().getRuneCategories().getFirst().getStyle()).isEqualTo("8000");
            assertThat(darius.getRunes().getRuneCategories().getFirst().getPerks())
                    .containsExactly("8010", "9111", "9104", "8299");
            assertThat(darius.getAugment1()).isNull();

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

            assertThat(result.getId()).isEqualTo("EUW1_7941319192");
            assertThat(result.getVersion()).isEqualTo("16.15");
            assertThat(result.getSeason()).isEqualTo(16);
            assertThat(result.getDatetime()).isEqualTo(Instant.parse("2026-08-05T18:30:37.751Z"));
            assertThat(result.getDuration()).isEqualTo(1411L);
            assertThat(result.getQueue()).isEqualTo(QueueMappingHelper.toQueue(1750));
            assertThat(result.isRemake()).isFalse();
            assertThat(result.getOwners()).isNullOrEmpty();

            // 6 équipes de 3 invocateurs triées par placement (1 -> 6)
            assertThat(result.getTeams()).hasSize(6);
            assertThat(result.getTeams())
                    .extracting(Team::getId, Team::getPlacement, Team::isHasWin)
                    .containsExactly(
                            org.assertj.core.groups.Tuple.tuple("5", 1, true),
                            org.assertj.core.groups.Tuple.tuple("1", 2, true),
                            org.assertj.core.groups.Tuple.tuple("4", 3, true),
                            org.assertj.core.groups.Tuple.tuple("2", 4, false),
                            org.assertj.core.groups.Tuple.tuple("6", 5, false),
                            org.assertj.core.groups.Tuple.tuple("3", 6, false)
                    );

            // Team 5 (placement 1) : Braum, Draven, Seraphine
            Team firstTeam = result.getTeams().getFirst();
            assertThat(firstTeam.getObjectives()).isNull();
            assertThat(firstTeam.getBans()).isNullOrEmpty();
            assertThat(firstTeam.getParticipants()).hasSize(3);

            Participant braum = firstTeam.getParticipants().getFirst();
            assertThat(braum.getChampion()).isEqualTo("201");
            assertThat(braum.getRole()).isEqualTo("INVALID");
            assertThat(braum.getSummoner().getName()).isEqualTo("a 200 dans lmur");
            assertThat(braum.getSummoner().getTag()).isEqualTo("dead");
            assertThat(braum.getAugment1()).isEqualTo("193");
            assertThat(braum.getAugment2()).isEqualTo("216");
            assertThat(braum.getAugment3()).isEqualTo("219");
            assertThat(braum.getAugment4()).isNull();

            Participant draven = firstTeam.getParticipants().get(1);
            assertThat(draven.getChampion()).isEqualTo("119");
            assertThat(draven.getItem0()).isEqualTo("220012");
            assertThat(draven.getItem1()).isEqualTo("447101");
            assertThat(draven.getItem2()).isNull();
            assertThat(draven.getAugment1()).isEqualTo("220");
            assertThat(draven.getAugment5()).isEqualTo("200");
            assertThat(draven.getAugment6()).isNull();

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