package com.krazytop.leagueoflegends.infrastructure.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "league-of-legends")
public class RiotProperties {

    /**
     * Clé d'authentification pour l'API de Riot Games (League of Legends seulement).
     */
    @NotBlank(message = "La clé API Riot (league-of-legends.api-key) doit être renseignée")
    private String apiKey;
}