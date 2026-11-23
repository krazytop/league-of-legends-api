package com.krazytop.leagueoflegends.domain.model.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class PageResult<T> {

    private List<T> content = new ArrayList<>();
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}