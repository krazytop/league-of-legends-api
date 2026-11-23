package com.krazytop.leagueoflegends.domain.exception;

public class InvalidSearchCriteriaException extends RuntimeException {

    public InvalidSearchCriteriaException(String message) {
        super(message);
    }
}