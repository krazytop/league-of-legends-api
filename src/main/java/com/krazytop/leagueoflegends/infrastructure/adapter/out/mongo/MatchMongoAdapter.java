package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;
import com.krazytop.leagueoflegends.domain.port.out.match.MatchRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.common.QueueMappingHelper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.MatchDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.MatchMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.MatchMongoRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MatchMongoAdapter implements MatchRepositoryPort {

    private final MatchMongoRepository repository;
    private final MatchMongoMapper mapper;
    private final MongoTemplate mongoTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResult<Match> findMatches(PageRequest<MatchSearchCriteria> pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                Sort.by(Sort.Direction.DESC, "datetime")
        );

        Query query = buildSearchQuery(pageRequest.getSearchCriteria());

        long total = mongoTemplate.count(query, MatchDocument.class);
        List<MatchDocument> documents = mongoTemplate.find(query.with(pageable), MatchDocument.class);

        Page<MatchDocument> page = PageableExecutionUtils.getPage(documents, pageable, () -> total);
        return mapper.toPageResult(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> findExistingMatchIdsByOwner(List<String> matchIds, String puuid) {
        if (matchIds == null || matchIds.isEmpty()) {
            return Set.of();
        }

        Query query = new Query(Criteria.where("_id").in(matchIds).and("owners").is(puuid));
        query.fields().include("_id");

        return mongoTemplate.find(query, MatchDocument.class).stream()
                .map(MatchDocument::getId)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Match> addOwnerIfMatchExists(String matchId, String puuid) {
        Query query = Query.query(Criteria.where("_id").is(matchId));

        // Projection : évite de remonter trop d'infos qui ne nous intéressent pas
        query.fields()
                .include("duration")
                .include("season")
                .include("queue")
                .elemMatch("participants", Criteria.where("puuid").is(puuid));

        Update update = new Update().addToSet("owners", puuid);

        MatchDocument match = mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(false),
                MatchDocument.class
        );

        return Optional.ofNullable(match).map(mapper::toModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMatch(Match match) {
        repository.save(
                mapper.toDocument(match));
    }

    /**
     * Construit la requête de recherche des matchs à partir des critères.
     *
     * @param searchCriteria les critères de recherche
     * @return la requête de recherche
     */
    private Query buildSearchQuery(MatchSearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            return new Query();
        }

        List<Criteria> criteriaList = new ArrayList<>();

        if (StringUtils.isNotBlank(searchCriteria.getPuuid())) {
            criteriaList.add(Criteria.where("owners").is(searchCriteria.getPuuid()));
        }
        if (searchCriteria.getQueue()  != null) {
            criteriaList.add(Criteria.where("queue").in(QueueMappingHelper.resolveQueueIds(searchCriteria.getQueue())));
        }
        if (searchCriteria.getSeason() != null) {
            criteriaList.add(Criteria.where("season").is(searchCriteria.getSeason()));
        }
        if (searchCriteria.getRole() != null && StringUtils.isNotBlank(searchCriteria.getPuuid())) {
            Criteria participantCriteria = Criteria.where("role").is(searchCriteria.getRole().getValue());
            participantCriteria.and("summoner.puuid").is(searchCriteria.getPuuid());
            criteriaList.add(Criteria.where("teams.participants").elemMatch(participantCriteria));
        }

        return criteriaList.isEmpty() ? new Query() : new Query(new Criteria().andOperator(criteriaList));
    }
}
