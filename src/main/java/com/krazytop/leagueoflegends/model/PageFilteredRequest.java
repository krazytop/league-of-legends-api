package com.krazytop.leagueoflegends.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageFilteredRequest<T> extends PageRequest {

    private T filters;

    public PageFilteredRequest(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }

}
