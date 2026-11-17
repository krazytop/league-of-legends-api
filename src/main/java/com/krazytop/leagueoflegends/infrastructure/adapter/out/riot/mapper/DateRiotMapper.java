package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.mapper;

import org.mapstruct.Mapper;

import java.time.Instant;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DateRiotMapper {

    default Instant epochMillisToInstant(long epochMillis) {
        return Instant.ofEpochMilli(epochMillis);
    }
}
