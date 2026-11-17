package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.QueueRanks;
import com.krazytop.leagueoflegends.domain.port.out.rank.RankRiotPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.RankRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RankRiotAdapter implements RankRiotPort {

    private final R4J r4j;
    private final ShardRiotMapper shardMapper;
    private final RankRiotMapper rankMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QueueRanks> fetchRanks(EnumRegion region, String puuid) {
        LeagueShard shard = shardMapper.toDTO(region);

        List<LeagueEntry> ranks = r4j.getLoLAPI()
                .getLeagueAPI()
                .getLeagueEntriesByPUUID(shard, puuid);

        return ranks.stream()
                .map(rankMapper::toModel)
                .toList();
    }
}
