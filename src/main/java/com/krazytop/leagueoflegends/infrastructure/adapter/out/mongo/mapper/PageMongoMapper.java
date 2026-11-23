package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import org.springframework.data.domain.Page;

public interface PageMongoMapper<D, M> {

    M toModel(D document);

    default PageResult<M> toPageResult(Page<D> page) {
        return new PageResult<M>()
                .setContent(page.getContent().stream().map(this::toModel).toList())
                .setPageNumber(page.getNumber())
                .setPageSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }
}