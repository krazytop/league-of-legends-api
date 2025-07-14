package com.krazytop.leagueoflegends.service;

import com.krazytop.leagueoflegends.entity.Board;
import com.krazytop.leagueoflegends.entity.Summoner;
import com.krazytop.leagueoflegends.exception.ApiErrorEnum;
import com.krazytop.leagueoflegends.exception.CustomException;
import com.krazytop.leagueoflegends.mapper.BoardMapper;
import com.krazytop.leagueoflegends.model.generated.BoardDTO;
import com.krazytop.leagueoflegends.model.generated.SummonerDTO;
import com.krazytop.leagueoflegends.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final SummonerService summonerService;
    private final MatchService matchService;
    private final MasteryService masteryService;
    private final RankService rankService;
    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardRepository boardRepository, SummonerService summonerService, MatchService matchService, MasteryService masteryService, RankService rankService, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.summonerService = summonerService;
        this.matchService = matchService;
        this.masteryService = masteryService;
        this.rankService = rankService;
        this.boardMapper = boardMapper;
    }

    public BoardDTO getBoardDTO(String boardId) {
        return boardMapper.toDTO(getBoard(boardId));
    }

    public Board getBoard(String boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ApiErrorEnum.BOARD_NOT_FOUND));
    }

    public String createBoard() {
        Board board = new Board();
        boardRepository.save(board);
        return board.getId();
    }

    public SummonerDTO addSummonerToBoard(String boardId, String tag, String name) {
        Board board = getBoard(boardId);
        SummonerDTO summoner = summonerService.getSummonerDTO(tag, name);
        if (!board.getPuuids().contains(summoner.getPuuid())) {
            board.getPuuids().add(summoner.getPuuid());
            boardRepository.save(board);
        } else {
            throw new CustomException(ApiErrorEnum.SUMMONER_ALREADY_ADDED_TO_BOARD);
        }
        return summoner;
    }

    public void removeSummonerOfBoard(String boardId, String puuid) {
        Board board = getBoard(boardId);
        if (board.getPuuids().contains(puuid)) {
            board.getPuuids().remove(puuid);
            boardRepository.save(board);
        } else {
            throw new CustomException(ApiErrorEnum.SUMMONER_ABSENT_OF_BOARD);
        }
    }

    public void updateBoard(String boardId) {
        Board board = getBoard(boardId);
        board.getPuuids().forEach(puuid -> {
            Summoner summoner = summonerService.getSummoner(puuid);
            if (summoner.getUpdateDate() != null) {
                summonerService.updateSummoner(summoner.getPuuid());
                rankService.updateRanks(summoner.getPuuid());
                matchService.updateMatches(summoner.getPuuid());
                masteryService.updateMasteries(summoner.getPuuid());
            }
        });
        board.setUpdateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        boardRepository.save(board);
    }

    public void updateBoardName(String boardId, String name) {
        Board board = getBoard(boardId);
        board.setName(name);
        boardRepository.save(board);
    }

    public void deleteBoard(String boardId) {
        getBoard(boardId);
        boardRepository.deleteById(boardId);
    }

}
