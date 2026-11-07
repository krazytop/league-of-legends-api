package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.entity.MatchSearchCriteria;
import com.krazytop.leagueoflegends.entity.PageableSearchCriteria;
import org.springframework.data.domain.Page;

public interface MatchRepositoryCustom {

    Page<Match> findAll(PageableSearchCriteria<MatchSearchCriteria> pageableSearchCriteria);
}