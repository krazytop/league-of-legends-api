package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import com.krazytop.leagueoflegends.domain.model.Account;
import no.stelar7.api.r4j.pojo.shared.RiotAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AccountRiotMapper {

    @Mapping(target = "puuid", source = "PUUID")
    Account toModel(RiotAccount account);
}
