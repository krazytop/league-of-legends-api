package com.krazytop.leagueoflegends.domain.exception;

public class TooManySummonersException extends RuntimeException {

    public TooManySummonersException(String boardId, String puuid) {
        super("L'invocateur %s ne peux pas être ajouté sur le board %s car le nombre d'invocateur maximum est atteint".formatted(puuid, boardId));
    }
}