package com.krazytop.leagueoflegends.infrastructure.adapter.in.rest;

import com.krazytop.leagueoflegends.api.generated.BoardApi;
import com.krazytop.leagueoflegends.domain.model.BoardUpdateRequest;
import com.krazytop.leagueoflegends.domain.service.BoardService;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.BoardRestMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.in.rest.mapper.SummonerRestMapper;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import com.krazytop.leagueoflegends.model.generated.BoardUpdateRequestDTO;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class BoardController implements BoardApi {

    private final BoardService service;
    private final BoardRestMapper boardMapper;
    private final SummonerRestMapper summonerMapper;

    @Override
    public ResponseEntity<BoardDTO> createBoard() {
        log.info("Création d'un nouveau board");
        BoardDTO board = boardMapper.toDTO(
                service.createBoard());
        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }

    @Override
    public ResponseEntity<BoardDTO> getBoard(String boardId) {
        log.info("Récupération du board {}", boardId);
        BoardDTO board = boardMapper.toDTO(
                service.getBoard(boardId));
        return ResponseEntity.ok(board);
    }

    @Override
    public ResponseEntity<BoardDTO> patchBoard(String boardId, BoardUpdateRequestDTO requestDTO) {
        log.info("Mise à jour partielle du board {}", boardId);
        BoardUpdateRequest request = boardMapper.toModel(requestDTO);
        BoardDTO board = boardMapper.toDTO(
                service.patchBoard(boardId, request));
        return ResponseEntity.ok(board);
    }

    @Override
    public ResponseEntity<Void> deleteBoard(String boardId) {
        log.info("Suppression du board {}", boardId);
        service.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateBoard(String boardId) {
        log.info("Rafraîchissement des données du board {}", boardId);
        service.updateBoard(boardId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SummonerDTO> addSummonerToBoard(String boardId, String tag, String name) {
        log.info("Ajout de l'invocateur {} #{} au board {}", name, tag, boardId);
        SummonerDTO summoner = summonerMapper.toDTO(
                service.addSummonerToBoard(boardId, tag, name));
        return ResponseEntity.ok(summoner);
    }

    @Override
    public ResponseEntity<Void> removeSummonerFromBoard(String boardId, String puuid) {
        log.info("Suppression de l'invocateur avec le puuid {} du board {}", puuid, boardId);
        service.removeSummonerFromBoard(boardId, puuid);
        return ResponseEntity.ok().build();
    }
}