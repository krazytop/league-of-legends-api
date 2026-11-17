package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.model.RankInformations;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = DateRiotMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RankRiotMapper {

    default QueueRanks toModel(LeagueEntry queueRanks) {
        String name = queueRanks.getQueueType().getApiName();
        RankInformations rankInformations = new RankInformations()
                .setLeaguePoints(queueRanks.getLeaguePoints())
                .setRank(queueRanks.getRank())
                .setTier(queueRanks.getTier())
                .setWins(queueRanks.getWins())
                .setLosses(queueRanks.getLosses());
        return new QueueRanks()
                .setName(name)
                .setRankInformations(List.of(rankInformations));
    }
}
