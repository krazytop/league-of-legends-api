package com.krazytop.leagueoflegends.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QueueRanksTest {

    @Test
    void mergeRankInformations_shouldAddAllDistinctEntries_whenHistoryIsEmpty() {
        QueueRanks queueRanks = new QueueRanks();
        RankInformations first = rank(10, 5);
        RankInformations second = rank(11, 5);

        queueRanks.mergeRankInformations(List.of(first, second));

        assertThat(queueRanks.getRankInformations()).containsExactly(first, second);
    }

    @Test
    void mergeRankInformations_shouldIgnoreDuplicatesWithinSameBatch_whenHistoryIsEmpty() {
        QueueRanks queueRanks = new QueueRanks();
        RankInformations first = rank(10, 5);
        RankInformations duplicate = rank(10, 5);
        RankInformations next = rank(10, 6);

        queueRanks.mergeRankInformations(List.of(first, duplicate, next));

        assertThat(queueRanks.getRankInformations()).containsExactly(first, next);
    }

    @Test
    void mergeRankInformations_shouldOnlyAppendEntriesWithDifferentMatchCount() {
        RankInformations initial = rank(10, 5);
        QueueRanks queueRanks = new QueueRanks()
                .setRankInformations(List.of(initial));

        RankInformations unchanged = rank(10, 5);
        RankInformations playedOne = rank(11, 5);
        RankInformations unchangedAfterOne = rank(11, 5);
        RankInformations playedTwo = rank(11, 6);

        queueRanks.mergeRankInformations(List.of(unchanged, playedOne, unchangedAfterOne, playedTwo));

        assertThat(queueRanks.getRankInformations())
                .containsExactly(initial, playedOne, playedTwo);
    }

    private RankInformations rank(int wins, int losses) {
        return new RankInformations()
                .setWins(wins)
                .setLosses(losses);
    }
}