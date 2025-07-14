package com.krazytop.leagueoflegends.controller;

import com.krazytop.leagueoflegends.api.generated.BoardApi;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import com.krazytop.leagueoflegends.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController implements BoardApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Override
    public ResponseEntity<BoardDTO> getBoard(@PathVariable String boardId) {
        LOGGER.info("Retrieving board");
        BoardDTO board = boardService.getBoardDTO(boardId);
        LOGGER.info("Board retrieved");
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateBoard(@PathVariable String boardId) {
        LOGGER.info("Updating board");
        boardService.updateBoard(boardId);
        LOGGER.info("Board updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createBoard() {
        LOGGER.info("Creating board");
        String boardId = boardService.createBoard();
        LOGGER.info("Board created");
        return new ResponseEntity<>(boardId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SummonerDTO> addSummonerToBoard(@PathVariable String boardId, @PathVariable String tag, @PathVariable String name) {
        LOGGER.info("Updating board with the new summoner");
        SummonerDTO newSummoner = boardService.addSummonerToBoard(boardId, tag, name);
        LOGGER.info("Board updated with the new summoner");
        return new ResponseEntity<>(newSummoner, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeSummonerOfBoard(@PathVariable String boardId, @PathVariable String puuid) {
        LOGGER.info("Removing summoner of the board");
        boardService.removeSummonerOfBoard(boardId, puuid);
        LOGGER.info("Summoner removed of the board");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateBoardName(@PathVariable String boardId, @PathVariable String name) {
        LOGGER.info("Updating board name");
        boardService.updateBoardName(boardId, name);
        LOGGER.info("Board name updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteBoard(@PathVariable String boardId) {
        LOGGER.info("Deleting board");
        boardService.deleteBoard(boardId);
        LOGGER.info("Board deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}