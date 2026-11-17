package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot;

import com.krazytop.leagueoflegends.domain.model.Account;
import com.krazytop.leagueoflegends.domain.model.EnumGlobalRegion;
import com.krazytop.leagueoflegends.domain.model.EnumRegion;
import com.krazytop.leagueoflegends.domain.port.out.account.AccountRiotPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.AccountRiotMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper.ShardRiotMapper;
import lombok.AllArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.api.regions.ShardableGame;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import no.stelar7.api.r4j.pojo.shared.RiotAccountRegion;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AccountRiotAdapter implements AccountRiotPort {

    private final R4J r4j;
    private final ShardRiotMapper shardMapper;
    private final AccountRiotMapper accountMapper;

    /**
     * {@inheritDoc}
     */
    public Optional<Account> fetchAccount(EnumGlobalRegion globalRegion, String puuid) {
        RegionShard shard = shardMapper.toDTO(globalRegion);

        RiotAccount account = r4j.getAccountAPI()
                .getAccountByPUUID(shard, puuid);

        return Optional.ofNullable(account)
                .map(a -> accountMapper.toModel(a)
                .setRegion(fetchAccountRegion(globalRegion, puuid)));
    }

    /**
     * {@inheritDoc}
     */
    public Optional<Account> fetchAccount(EnumGlobalRegion globalRegion, String name, String tag) {
        RegionShard shard = shardMapper.toDTO(globalRegion);

        RiotAccount account = r4j.getAccountAPI()
                .getAccountByTag(shard, name, tag);

        return Optional.ofNullable(account)
                .map(a -> accountMapper.toModel(a)
                        .setRegion(fetchAccountRegion(globalRegion, account.getPUUID())));
    }

    /**
     * Récupère la region associée à l'invocateur
     *
     * @param globalRegion région globale de l'invocateur
     * @param puuid le puuid de l'invocateur
     * @return la région de l'invocateur
     */
    private EnumRegion fetchAccountRegion(EnumGlobalRegion globalRegion, String puuid) {
        RegionShard shard = shardMapper.toDTO(globalRegion);

        return Optional.ofNullable(r4j.getAccountAPI()
                .getActiveRegion(shard, ShardableGame.LOL, puuid))
                .map(RiotAccountRegion::getRegion)
                .map(EnumRegion::fromCode)
                .orElse(null);
    }
}
