package com.krazytop.leagueoflegends.domain.model.common;

import lombok.Data;

@Data
public final class PageRequest<T> {

    private int pageNumber;
    private int pageSize;
    private T searchCriteria;
}