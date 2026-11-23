package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.model.match.*;
import no.stelar7.api.r4j.pojo.lol.match.v5.*;
import org.mapstruct.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = DateRiotMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MatchRiotMapper {

    @Mapping(target = "version", source = "gameVersion", qualifiedByName = "mapVersion")
    @Mapping(target = "season", source = "gameVersion", qualifiedByName = "mapSeason")
    @Mapping(target = "datetime", source = "gameStartTimestamp")
    @Mapping(target = "duration", source = "gameDuration")
    @Mapping(target = "remake", source = "participants", qualifiedByName = "mapRemake")
    @Mapping(target = "teams", source = ".", qualifiedByName = "mapTeams")
    Match toModel(LOLMatch match);

    @Named("mapTeams")
    default List<Team> mapTeams(LOLMatch lolMatch) {
        List<MatchParticipant> allParticipants = emptyIfNull(lolMatch.getParticipants());

        boolean isArena = allParticipants.stream()
                .anyMatch(p -> p.getPlayerSubteamId() > 0);

        return isArena ? mapArenaTeams(allParticipants) : mapClassicTeams(lolMatch.getTeams(), allParticipants);
    }

    private List<Team> mapClassicTeams(List<MatchTeam> teams, List<MatchParticipant> matchParticipants) {
        return emptyIfNull(teams).stream()
                .map(team -> {
                    List<Participant> teamParticipants = matchParticipants.stream()
                            .filter(p -> Objects.equals(p.getTeam().getValue(), team.getTeamId().getValue()))
                            .map(this::toParticipantModel)
                            .toList();

                    return toTeamModel(team)
                            .setParticipants(teamParticipants);
                })
                .toList();
    }

    private List<Team> mapArenaTeams(List<MatchParticipant> participants) {
        return participants.stream()
                .collect(Collectors.groupingBy(MatchParticipant::getPlayerSubteamId))
                .entrySet().stream()
                .map(entry -> {
                    List<MatchParticipant> teamParticipants = entry.getValue();

                    return new Team()
                            .setId(String.valueOf(entry.getKey()))
                            .setHasWin(teamParticipants.getFirst().didWin())
                            .setPlacement(teamParticipants.getFirst().getPlacement())
                            .setParticipants(teamParticipants.stream().map(this::toParticipantModel).toList());
                })
                .sorted(Comparator.comparingInt(Team::getPlacement))
                .toList();
    }

    @Named("mapVersion")
    default String mapVersion(String version) {
        return version.replaceAll("^([^.]+\\.[^.]+)\\..*$", "$1");
    }

    @Named("mapSeason")
    default int mapSeason(String version) {
        return Integer.parseInt(version.split("\\.")[0]);
    }

    @Named("mapRemake")
    default boolean mapRemake(List<MatchParticipant> participants) {
        return emptyIfNull(participants).stream()
                .anyMatch(MatchParticipant::isGameEndedInEarlySurrender);
    }

    @Named("mapBans")
    default List<String> mapBans(List<ChampionBan> bans) {
        return emptyIfNull(bans).stream()
                .sorted(Comparator.comparingInt(ChampionBan::getPickTurn))
                .map(ban -> String.valueOf(ban.getChampionId()))
                .toList();
    }

    @Mapping(target = "id", source = "teamId.value")
    @Mapping(target = "hasWin", expression = "java(team.didWin())")
    @Mapping(target = "bans", source = "bans", qualifiedByName = "mapBans")
    Team toTeamModel(MatchTeam team);

    @Mapping(target = "summonerSpell1", source = "summoner1Id")
    @Mapping(target = "summonerSpell2", source = "summoner2Id")
    @Mapping(target = "champion", source = "championId")
    @Mapping(target = "champLevel", source = "championLevel")
    @Mapping(target = "golds", source = "goldEarned")
    @Mapping(target = "neutralMinions", source = "neutralMinionsKilled")
    @Mapping(target = "minions", source = "totalMinionsKilled")
    @Mapping(target = "augment1", source = "playerAugment1", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "augment2", source = "playerAugment2", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "augment3", source = "playerAugment3", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "augment4", source = "playerAugment4", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "augment5", source = "playerAugment5", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "augment6", source = "playerAugment6", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "item0", source = "item0", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "item1", source = "item1", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "item2", source = "item2", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "item3", source = "item3", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "item4", source = "item4", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "item5", source = "item5", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "ward", source = "item6", qualifiedByName = "mapZeroToNull")
    @Mapping(target = "summoner", source = ".")
    @Mapping(target = "runes", source = "perks")
    @Mapping(target = "role", source = "gameDeterminedPosition")
    Participant toParticipantModel(MatchParticipant participant);

    default Integer map(ObjectiveStats value) {
        return value != null ? value.getKills() : 0;
    }

    @Mapping(target = "tag", source = "riotIdTagline")
    @Mapping(target = "name", source = "riotIdName")
    @Mapping(target = "icon", source = "profileIcon")
    @Mapping(target = "level", source = "summonerLevel")
    Summoner toSummoner(MatchParticipant participant);

    @Named("mapZeroToNull")
    default String mapZeroToNull(int value) {
        return value == 0 ? null : String.valueOf(value);
    }

    @Mapping(target = "stats", source = "statPerks")
    @Mapping(target = "runeCategories", source = "perkStyles")
    Runes toRunesModel(MatchPerks perks);

    @Mapping(target = "style", source = "style")
    @Mapping(target = "perks", source = "selections", qualifiedByName = "mapPerkSelections")
    RuneCategory toRuneCategoryModel(PerkStyle style);

    @Named("mapPerkSelections")
    default List<String> mapPerkSelections(List<PerkSelection> selections) {
        return emptyIfNull(selections).stream()
                .map(selection -> String.valueOf(selection.getPerk()))
                .toList();
    }

    @AfterMapping
    default void cleanEmptyRunes(@MappingTarget Participant participant) {
        Runes runes = participant.getRunes();
        if (runes == null) {
            return;
        }

        boolean hasNoStats = runes.getStats() == null || (
                "0".equals(runes.getStats().getDefense())
                        && "0".equals(runes.getStats().getFlex())
                        && "0".equals(runes.getStats().getOffense())
        );

        boolean hasNoCategories = runes.getRuneCategories() == null || runes.getRuneCategories().stream()
                .allMatch(cat -> "0".equals(cat.getStyle()));

        if (hasNoStats && hasNoCategories) {
            participant.setRunes(null);
        }
    }
}