package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.port.out.summoner.SummonerRiotPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.SummonerRiotMapper;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SummonerRiotAdapter implements SummonerRiotPort {

    private final R4J r4j;
    private final ShardRiotMapper shardMapper;
    private final SummonerRiotMapper summonerMapper;

    /**
     * {@inheritDoc}
     */
    public Optional<Summoner> fetchSummoner(EnumRegion region, String puuid) {
        LeagueShard shard = shardMapper.toDTO(region);

        no.stelar7.api.r4j.pojo.lol.summoner.Summoner summoner = r4j.getLoLAPI()
                .getSummonerAPI()
                .getSummonerByPUUID(shard, puuid);

        return Optional.ofNullable(summoner)
                .map(summonerMapper::toModel);
    }
}
