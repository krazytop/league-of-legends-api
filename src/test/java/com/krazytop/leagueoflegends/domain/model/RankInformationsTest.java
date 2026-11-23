package com.krazytop.leagueoflegends.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankInformationsTest {

    @ParameterizedTest(name = "({0}W/{1}L) vs ({2}W/{3}L) -> {4}")
    @CsvSource({
            "10, 5, 10, 5, true",
            "10, 5,  9, 6, true",
            " 0, 0,  0, 0, true",
            "10, 5, 11, 5, false",
            "10, 5, 10, 6, false",
            " 0, 0,  1, 0, false"
    })
    void equalsOnMatchesCount_shouldCompareTotalMatches(int oldWins, int oldLosses, int newWins, int newLosses, boolean expected) {
        RankInformations oldRank = new RankInformations()
                .setWins(oldWins)
                .setLosses(oldLosses);
        RankInformations newRank = new RankInformations()
                .setWins(newWins)
                .setLosses(newLosses);

        assertThat(oldRank.equalsOnMatchesCount(newRank)).isEqualTo(expected);
    }
}