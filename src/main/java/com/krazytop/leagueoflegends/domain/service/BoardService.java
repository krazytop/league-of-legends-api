package com.krazytop.leagueoflegends.domain.service;

import com.krazytop.leagueoflegends.domain.exception.ResourceNotFoundException;
import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.domain.model.BoardUpdateRequest;
import com.krazytop.leagueoflegends.domain.model.Summoner;
import com.krazytop.leagueoflegends.domain.port.out.board.BoardRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BoardService {

    private static final String DEFAULT_BOARD_NAME = "Nouveau Board";

    private final BoardRepositoryPort repositoryPort;
    private final SummonerService summonerService;
    private final RankService rankService;
    private final MasteryService masteryService;
    private final MatchService matchService;
    private final Clock clock;

    /**
     * Crée un nouveau board vide avec un identifiant unique et un nom par défaut.
     *
     * @return le board créé
     */
    public Board createBoard() {
        Board board = new Board()
                .setId(UUID.randomUUID().toString())
                .setName(DEFAULT_BOARD_NAME)
                .setUpdateDate(Instant.now(clock));
        return repositoryPort.saveBoard(board);
    }

    /**
     * Récupère un board.
     *
     * @param boardId l'id du board
     * @return le board correspondant
     * @throws ResourceNotFoundException si le board n'existe pas
     */
    public Board getBoard(String boardId) {
        return repositoryPort.findBoard(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Le board %s n'existe pas".formatted(boardId)));
    }

    /**
     * Met à jour partiellement un board.
     *
     * @param boardId l'id du board
     * @param request les données du board à modifier
     * @return le board mis à jour
     */
    public Board patchBoard(String boardId, BoardUpdateRequest request) {
        Board board = getBoard(boardId);
        if (StringUtils.isNotBlank(request.getName())) {
            board.setName(request.getName().strip());
        }
        return repositoryPort.saveBoard(board);
    }

    /**
     * Supprime un board.
     *
     * @param boardId l'id du board
     */
    public void deleteBoard(String boardId) {
        repositoryPort.deleteBoard(boardId);
    }

    /**
     * Met à jour l'ensemble des données de tous les invocateurs d'un board.
     *
     * @param boardId l'id du board
     */
    public void updateBoard(String boardId) {
        Board board = getBoard(boardId);
        board.getPuuids().forEach(puuid -> {
            summonerService.updateSummoner(puuid);
            rankService.updateRanks(puuid);
            masteryService.updateMasteries(puuid);
            matchService.updateMatches(puuid, false);
        });
        board.setUpdateDate(Instant.now(clock));
        repositoryPort.saveBoard(board);
    }

    /**
     * Résout un invocateur par son tag et son nom, l'importe si nécessaire, puis l'ajoute au board.
     *
     * @param boardId l'id du board
     * @param tag le tag de l'invocateur
     * @param name le nom de l'invocateur
     * @return l'invocateur ajouté au board
     */
    public Summoner addSummonerToBoard(String boardId, String tag, String name) {
        Board board = getBoard(boardId);
        Summoner summoner = summonerService.getOrFetchSummoner(tag, name);
        board.addSummoner(summoner.getPuuid());
        repositoryPort.saveBoard(board);
        return summoner;
    }

    /**
     * Supprime un invocateur d'un board.
     *
     * @param boardId l'id du board
     * @param puuid le puuid de l'invocateur
     */
    public void removeSummonerFromBoard(String boardId, String puuid) {
        Board board = getBoard(boardId);
        board.removeSummoner(puuid);
        repositoryPort.saveBoard(board);
    }
}