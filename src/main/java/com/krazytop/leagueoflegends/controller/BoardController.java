package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.BoardApi;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import com.krazytop.leagueoflegends.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BoardController implements BoardApi {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Override
    public ResponseEntity<BoardDTO> getBoard(String boardId) {
        log.info("Retrieving board");
        BoardDTO board = boardService.getBoardDTO(boardId);
        log.info("Board retrieved");
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateBoard(String boardId) {
        log.info("Updating board");
        boardService.updateBoard(boardId);
        log.info("Board updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createBoard() {
        log.info("Creating board");
        String boardId = boardService.createBoard();
        log.info("Board created");
        return new ResponseEntity<>(boardId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SummonerDTO> addSummonerToBoard(String boardId, String tag, String name) {
        log.info("Updating board with the new summoner");
        SummonerDTO newSummoner = boardService.addSummonerToBoard(boardId, tag, name);
        log.info("Board updated with the new summoner");
        return new ResponseEntity<>(newSummoner, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeSummonerOfBoard(String boardId, String puuid) {
        log.info("Removing summoner of the board");
        boardService.removeSummonerOfBoard(boardId, puuid);
        log.info("Summoner removed of the board");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateBoardName(String boardId, String name) {
        log.info("Updating board name");
        boardService.updateBoardName(boardId, name);
        log.info("Board name updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteBoard(String boardId) {
        log.info("Deleting board");
        boardService.deleteBoard(boardId);
        log.info("Board deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}