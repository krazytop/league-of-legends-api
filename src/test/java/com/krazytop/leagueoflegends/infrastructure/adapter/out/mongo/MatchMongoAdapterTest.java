package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.common.PageRequest;
import com.krazytop.leagueoflegends.domain.model.common.PageResult;
import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import com.krazytop.leagueoflegends.domain.model.enums.EnumRole;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import com.krazytop.leagueoflegends.domain.model.searchcriteria.MatchSearchCriteria;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.common.QueueMappingHelper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.match.MatchDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.MatchMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.MatchMongoRepository;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchMongoAdapterTest {

    private static final String PUUID = "OuXbh5J3r5AhEXP7-yvlgibfYpi1eqRxC_najyra9KsPAw8mo4xblx93h9T5TQdtb7fR5CUYHEiBKw";
    private static final String MATCH_ID_1 = "EUW1_7939943973";
    private static final String MATCH_ID_2 = "EUW1_7941319192";

    @Mock
    private MatchMongoRepository repository;

    @Mock
    private MatchMongoMapper mapper;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MatchMongoAdapter adapter;

    @Test
    void findMatches_withNullSearchCriteria_shouldQueryWithoutFilterAndSortByDatetimeDesc() {
        // Arrange
        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<MatchSearchCriteria>()
                .setPageNumber(1)
                .setPageSize(10)
                .setSearchCriteria(null);

        MatchDocument doc = new MatchDocument();
        List<MatchDocument> docs = List.of(doc);
        Match domainMatch = new Match().setId(MATCH_ID_1);
        PageResult<Match> expectedResult = new PageResult<Match>()
                .setPageNumber(1)
                .setPageSize(10)
                .setTotalElements(1L)
                .setTotalPages(1)
                .setContent(List.of(domainMatch));

        when(mongoTemplate.count(any(Query.class), eq(MatchDocument.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(MatchDocument.class))).thenReturn(docs);
        when(mapper.toPageResult(any(Page.class))).thenReturn(expectedResult);

        // Act
        PageResult<Match> result = adapter.findMatches(pageRequest);

        // Then
        assertThat(result).isEqualTo(expectedResult);

        ArgumentCaptor<Query> countQueryCaptor = ArgumentCaptor.forClass(Query.class);
        ArgumentCaptor<Query> findQueryCaptor = ArgumentCaptor.forClass(Query.class);

        verify(mongoTemplate, times(1)).count(countQueryCaptor.capture(), eq(MatchDocument.class));
        verify(mongoTemplate, times(1)).find(findQueryCaptor.capture(), eq(MatchDocument.class));

        assertThat(countQueryCaptor.getValue().getQueryObject()).isEmpty();

        Query appliedFindQuery = findQueryCaptor.getValue();
        assertThat(appliedFindQuery.getSkip()).isEqualTo(10L);
        assertThat(appliedFindQuery.getLimit()).isEqualTo(10);
        assertThat(appliedFindQuery.getSortObject()).containsEntry("datetime", -1);

        ArgumentCaptor<Page<MatchDocument>> pageCaptor = ArgumentCaptor.forClass(Page.class);
        verify(mapper, times(1)).toPageResult(pageCaptor.capture());
        assertThat(pageCaptor.getValue().getContent()).containsExactly(doc);
        assertThat(pageCaptor.getValue().getTotalElements()).isEqualTo(11L);

        verifyNoMoreInteractions(mongoTemplate, mapper);
        verifyNoInteractions(repository);
    }

    @Test
    void findMatches_withBlankCriteria_shouldNotAddFilterCriteria() {
        // Arrange
        MatchSearchCriteria criteria = new MatchSearchCriteria()
                .setPuuid("   ")
                .setQueue(null)
                .setRole(null)
                .setSeason(null);

        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<MatchSearchCriteria>()
                .setPageNumber(1)
                .setPageSize(5)
                .setSearchCriteria(criteria);

        PageResult<Match> expectedResult = new PageResult<>();

        when(mongoTemplate.count(any(Query.class), eq(MatchDocument.class))).thenReturn(0L);
        when(mongoTemplate.find(any(Query.class), eq(MatchDocument.class))).thenReturn(List.of());
        when(mapper.toPageResult(any(Page.class))).thenReturn(expectedResult);

        // Act
        PageResult<Match> result = adapter.findMatches(pageRequest);

        // Then
        assertThat(result).isEqualTo(expectedResult);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate).count(queryCaptor.capture(), eq(MatchDocument.class));
        assertThat(queryCaptor.getValue().getQueryObject()).isEmpty();
    }

    @Test
    void findMatches_withPuuidQueueSeasonAndRole_shouldBuildAllCriteriaInAndOperator() {
        // Arrange
        MatchSearchCriteria criteria = new MatchSearchCriteria()
                .setPuuid(PUUID)
                .setQueue(EnumQueue.SOLO_RANKED)
                .setSeason(16)
                .setRole(EnumRole.BOTTOM);

        PageRequest<MatchSearchCriteria> pageRequest = new PageRequest<MatchSearchCriteria>()
                .setPageNumber(0)
                .setPageSize(10)
                .setSearchCriteria(criteria);

        PageResult<Match> expectedResult = new PageResult<>();

        when(mongoTemplate.count(any(Query.class), eq(MatchDocument.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(MatchDocument.class))).thenReturn(List.of(new MatchDocument()));
        when(mapper.toPageResult(any(Page.class))).thenReturn(expectedResult);

        // Act
        PageResult<Match> result = adapter.findMatches(pageRequest);

        // Then
        assertThat(result).isEqualTo(expectedResult);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate).count(queryCaptor.capture(), eq(MatchDocument.class));

        Document queryDoc = queryCaptor.getValue().getQueryObject();
        List<Document> andClauses = queryDoc.getList("$and", Document.class);

        assertThat(andClauses).hasSize(4);
        assertThat(andClauses).anySatisfy(d -> assertThat(d.getString("owners")).isEqualTo(PUUID));
        assertThat(andClauses).anySatisfy(d -> {
            assertThat(d.get("queue")).isInstanceOf(Document.class);
            Document inClause = (Document) d.get("queue");
            assertThat(inClause.getList("$in", String.class))
                    .containsExactlyInAnyOrderElementsOf(QueueMappingHelper.resolveQueueIds(EnumQueue.SOLO_RANKED));
        });
        assertThat(andClauses).anySatisfy(d -> assertThat(d.getInteger("season")).isEqualTo(16));
        assertThat(andClauses).anySatisfy(d -> {
            assertThat(d.get("teams.participants")).isInstanceOf(Document.class);
            Document elemMatch = ((Document) d.get("teams.participants")).get("$elemMatch", Document.class);
            assertThat(elemMatch)
                    .containsEntry("role", "BOT")
                    .containsEntry("summoner.puuid", PUUID);
        });
    }

    @Test
    void findExistingMatchIdsByOwner_withNullOrEmptyList_shouldReturnEmptySetWithoutQueryingMongo() {
        // Act & Then
        assertThat(adapter.findExistingMatchIdsByOwner(null, PUUID)).isEmpty();
        assertThat(adapter.findExistingMatchIdsByOwner(List.of(), PUUID)).isEmpty();

        verifyNoInteractions(mongoTemplate, repository, mapper);
    }

    @Test
    void findExistingMatchIdsByOwner_withMatchIds_shouldReturnMatchingIdsSet() {
        // Arrange
        List<String> candidateIds = List.of(MATCH_ID_1, MATCH_ID_2);

        MatchDocument doc1 = new MatchDocument().setId(MATCH_ID_1);

        when(mongoTemplate.find(any(Query.class), eq(MatchDocument.class))).thenReturn(List.of(doc1));

        // Act
        Set<String> result = adapter.findExistingMatchIdsByOwner(candidateIds, PUUID);

        // Then
        assertThat(result).containsExactly(MATCH_ID_1);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate, times(1)).find(queryCaptor.capture(), eq(MatchDocument.class));

        Document queryObject = queryCaptor.getValue().getQueryObject();
        Document fieldsObject = queryCaptor.getValue().getFieldsObject();

        assertThat(queryObject.getString("owners")).isEqualTo(PUUID);
        assertThat(queryObject.get("_id")).isInstanceOf(Document.class);
        assertThat(((Document) queryObject.get("_id")).getList("$in", String.class)).isEqualTo(candidateIds);
        assertThat(fieldsObject).containsEntry("_id", 1);

        verifyNoMoreInteractions(mongoTemplate);
        verifyNoInteractions(repository, mapper);
    }

    @Test
    void addOwnerIfMatchExists_whenMatchExists_shouldAddOwnerAndReturnMappedMatch() {
        // Arrange
        MatchDocument document = new MatchDocument();
        Match expectedMatch = new Match().setId(MATCH_ID_1);

        when(mongoTemplate.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(MatchDocument.class)
        )).thenReturn(document);
        when(mapper.toModel(document)).thenReturn(expectedMatch);

        // Act
        Optional<Match> result = adapter.addOwnerIfMatchExists(MATCH_ID_1, PUUID);

        // Then
        assertThat(result).isPresent().contains(expectedMatch);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        ArgumentCaptor<Update> updateCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<FindAndModifyOptions> optionsCaptor = ArgumentCaptor.forClass(FindAndModifyOptions.class);

        verify(mongoTemplate, times(1)).findAndModify(
                queryCaptor.capture(),
                updateCaptor.capture(),
                optionsCaptor.capture(),
                eq(MatchDocument.class)
        );

        Document queryObject = queryCaptor.getValue().getQueryObject();
        Document fieldsObject = queryCaptor.getValue().getFieldsObject();
        Document updateObject = updateCaptor.getValue().getUpdateObject();

        assertThat(queryObject.getString("_id")).isEqualTo(MATCH_ID_1);
        assertThat(fieldsObject)
                .containsEntry("duration", 1)
                .containsEntry("season", 1)
                .containsEntry("queue", 1);
        assertThat(updateObject.get("$addToSet")).isInstanceOf(Document.class);
        assertThat(((Document) updateObject.get("$addToSet")).getString("owners")).isEqualTo(PUUID);
        assertThat(optionsCaptor.getValue().isReturnNew()).isFalse();

        verify(mapper, times(1)).toModel(document);
        verifyNoMoreInteractions(mongoTemplate, mapper);
        verifyNoInteractions(repository);
    }

    @Test
    void addOwnerIfMatchExists_whenMatchDoesNotExist_shouldReturnEmptyOptional() {
        // Arrange
        when(mongoTemplate.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(MatchDocument.class)
        )).thenReturn(null);

        // Act
        Optional<Match> result = adapter.addOwnerIfMatchExists(MATCH_ID_1, PUUID);

        // Then
        assertThat(result).isEmpty();
        verify(mongoTemplate, times(1)).findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(MatchDocument.class)
        );
        verifyNoInteractions(mapper, repository);
    }

    @Test
    void saveMatch_shouldMapMatchToDocumentAndSaveInRepository() {
        // Arrange
        Match match = new Match().setId(MATCH_ID_1);
        MatchDocument document = new MatchDocument().setId(MATCH_ID_1);

        when(mapper.toDocument(match)).thenReturn(document);
        when(repository.save(document)).thenReturn(document);

        // Act
        adapter.saveMatch(match);

        // Then
        verify(mapper, times(1)).toDocument(match);
        verify(repository, times(1)).save(document);
        verifyNoMoreInteractions(mapper, repository);
        verifyNoInteractions(mongoTemplate);
    }
}