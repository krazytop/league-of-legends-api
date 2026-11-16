package com.krazytop.leagueoflegends.cucumber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@Configuration
public class CucumberTimeConfig {

    public static final Instant INSTANT_ACTUEL = Instant.parse("2026-11-16T16:29:00Z");

    @Bean
    @Primary
    public Clock testClock() {
        return Clock.fixed(INSTANT_ACTUEL, ZoneOffset.UTC);
    }
}