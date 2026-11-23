package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.port.out.match.MatchRiotPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.common.QueueMappingHelper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.MatchRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.shared.GAMHSMatch;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MatchRiotAdapter implements MatchRiotPort {

    private final R4J r4j;
    private final ShardRiotMapper shardMapper;
    private final MatchRiotMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMatchIds(EnumRegion region, String puuid, int start, int count) {
        RegionShard shard = shardMapper.toDTO(region)
                .toRegionShard();

        return r4j.getLoLAPI()
                .getMatchAPI()
                .getMatchList(shard, puuid, null, null, start, count, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match getMatch(EnumRegion region, String matchId) {
        RegionShard shard = shardMapper.toDTO(region)
                .toRegionShard();

        GAMHSMatch rawMatch = new MatchBuilder(shard, matchId)
                .withContext(r4j.getContext())
                .getMatchRAW();

        if (rawMatch == null) {
            throw new IllegalStateException("Impossible de récupérer le match " + matchId);
        }

        LOLMatch match = rawMatch.asLOLMatch();
        int queueId = rawMatch.getInfo().get("queueId").getAsInt();

        return mapper.toModel(match)
                .setId(matchId)
                .setQueue(QueueMappingHelper.toQueue(queueId));
    }
}