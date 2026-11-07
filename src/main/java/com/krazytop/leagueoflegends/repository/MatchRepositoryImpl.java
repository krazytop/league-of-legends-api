package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.Match;
import com.krazytop.leagueoflegends.entity.MatchSearchCriteria;
import com.krazytop.leagueoflegends.entity.PageableSearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MatchRepositoryImpl implements MatchRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Match> findAll(PageableSearchCriteria<MatchSearchCriteria> pageableSearchCriteria) {
        MatchSearchCriteria criteria = pageableSearchCriteria.getCriteria();
        Query query = new Query().with(pageableSearchCriteria.getPageable());
        List<Criteria> andCriteria = new ArrayList<>();

        // Owner
        andCriteria.add(Criteria.where("owners").is(criteria.getPuuid()));

        // Queue
        if (criteria.getQueue() != null) {
            andCriteria.add(Criteria.where("queue").in(criteria.getQueue().getIds()));
        }

        // Role
        if (criteria.getRole() != null) {
            andCriteria.add(Criteria.where("teams.participants").elemMatch(
                    Criteria.where("summoner.puuid").is(criteria.getPuuid()).and("role").is(criteria.getRole().getRiotName())
            ));
        }

        query.addCriteria(new Criteria().andOperator(andCriteria.toArray(new Criteria[0])));

        List<Match> results = mongoTemplate.find(query, Match.class);

        // Gestion de la pagination (Count intelligent)
        return PageableExecutionUtils.getPage(
                results,
                pageableSearchCriteria.getPageable(),
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Match.class)
        );
    }
}