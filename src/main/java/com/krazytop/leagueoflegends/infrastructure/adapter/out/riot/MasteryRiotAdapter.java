package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.Mastery;
import com.krazytop.leagueoflegends.domain.port.out.mastery.MasteryRiotPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.MasteryRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.championmastery.ChampionMastery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MasteryRiotAdapter implements MasteryRiotPort {

    private final R4J r4j;
    private final ShardRiotMapper shardMapper;
    private final MasteryRiotMapper masteryMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Mastery> fetchMasteries(EnumRegion region, String puuid) {
        LeagueShard shard = shardMapper.toDTO(region);

        List<ChampionMastery> masteries = r4j.getLoLAPI()
                .getMasteryAPI()
                .getChampionMasteries(shard, puuid);

        return masteries.stream()
                .map(masteryMapper::toModel)
                .toList();
    }
}
