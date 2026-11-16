package com.krazytop.leagueoflegends.cucumber.config;

import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class CucumberContext {

    private final Map<String, String> placeholders = new HashMap<>();

    @PostConstruct
    public void init() {
        placeholders.put("@INSTANT_ACTUEL@", CucumberTimeConfig.INSTANT_ACTUEL.toString());
    }

    public void putPlaceholder(String key, String value) {
        placeholders.put(key, value);
    }

    public String resolve(String text) {
        if (text == null) {
            return null;
        }
        String resolved = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            resolved = resolved.replace(entry.getKey(), entry.getValue());
        }
        return resolved;
    }
}