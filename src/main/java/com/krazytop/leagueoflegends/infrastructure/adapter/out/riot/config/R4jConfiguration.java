package com.krazytop.leagueoflegends.infrastructure.adapter.out.riot.config;

import com.krazytop.leagueoflegends.infrastructure.config.RiotProperties;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class R4jConfiguration {

    @Bean
    public R4J r4j(RiotProperties properties) {
        APICredentials credentials = new APICredentials(properties.getApiKey());
        return new R4J(credentials);
    }
}