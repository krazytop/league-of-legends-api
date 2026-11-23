package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.port.out.arenacompletion.ArenaCompletionRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ArenaCompletionService {

    private final ArenaCompletionRepositoryPort repositoryPort;

    /**
     * Récupère les champions avec lesquels un invocateur a gagné en Arena.
     *
     * @param puuid le puuid de l'invocateur
     * @return la liste des champions
     */
    public Set<String> getArenaCompletion(String puuid) {
        return repositoryPort.findArenaCompletion(puuid);
    }

    public void saveArenaCompletion(Set<String> champions, String puuid) {
        repositoryPort.saveArenaCompletion(champions, puuid);
    }
}
