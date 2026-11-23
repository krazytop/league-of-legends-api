package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.match.FailedMatch;
import com.krazytop.leagueoflegends.domain.port.out.match.FailedMatchRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.FailedMatchDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.FailedMatchMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.FailedMatchMongoRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FailedMatchMongoAdapter implements FailedMatchRepositoryPort {

    private final FailedMatchMongoRepository repository;
    private final FailedMatchMongoMapper mapper;
    private final MongoTemplate mongoTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> findExistingFailedMatchIdsByOwner(List<String> matchIds, String puuid) {
        if (matchIds == null || matchIds.isEmpty()) {
            return Set.of();
        }

        Query query = new Query(Criteria.where("_id").in(matchIds).and("owners").is(puuid));
        query.fields().include("_id");

        return mongoTemplate.find(query, FailedMatchDocument.class).stream()
                .map(FailedMatchDocument::getId)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addOwnerIfFailedMatchExists(String matchId, String puuid) {
        Query query = new Query(Criteria.where("_id").is(matchId));
        Update update = new Update().addToSet("owners", puuid);

        UpdateResult result = mongoTemplate.updateFirst(query, update, FailedMatchDocument.class);
        return result.getMatchedCount() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveFailedMatch(FailedMatch failedMatch) {
        repository.save(
                mapper.toDocument(failedMatch));
    }
}