package com.krazytop.leagueoflegends.domain.exception;

public class SummonerAlreadyInBoardException extends RuntimeException {

    public SummonerAlreadyInBoardException(String boardId, String puuid) {
        super("L'invocateur %s est déjà dans le board %s".formatted(puuid, boardId));
    }
}