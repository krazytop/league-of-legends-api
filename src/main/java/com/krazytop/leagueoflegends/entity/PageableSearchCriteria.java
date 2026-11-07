package com.krazytop.leagueoflegends.entity;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Data
public class PageableSearchCriteria<T> {

    private final Sort sort;
    private final int pageNumber;
    private final int pageSize;
    private T criteria;

    public Pageable getPageable() {
        return sort == null ? PageRequest.of(pageNumber, pageSize) : PageRequest.of(pageNumber, pageSize, sort);
    }
}
