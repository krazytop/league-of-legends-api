package com.krazytop.leagueoflegends.domain.model;

import com.krazytop.leagueoflegends.domain.model.enums.EnumRegion;
import com.krazytop.leagueoflegends.domain.model.match.Match;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SummonerTest {

    @Test
    void updateWithAccount_shouldUpdateAccountFields() {
        // Arrange
        Summoner summoner = new Summoner()
                .setName("OldName")
                .setTag("OLD")
                .setRegion(EnumRegion.NA1);

        Account account = new Account()
                .setName("Krazytop")
                .setTag("EUW")
                .setRegion(EnumRegion.EUW1);

        // Act
        Summoner result = summoner.updateWith(account);

        // Assert
        assertThat(result).isSameAs(summoner);
        assertThat(summoner.getName()).isEqualTo("Krazytop");
        assertThat(summoner.getTag()).isEqualTo("EUW");
        assertThat(summoner.getRegion()).isEqualTo(EnumRegion.EUW1);
    }

    @Test
    void updateWithMatch_shouldIncrementSpentTimeAndAddSeason() {
        // Arrange
        Summoner summoner = new Summoner()
                .setSpentTime(1000L)
                .setPlayedSeasons(new HashSet<>(Set.of(13, 14)));

        Match match = new Match()
                .setDuration(1850L)
                .setSeason(15);

        // Act
        summoner.updateWith(match);

        // Assert
        assertThat(summoner.getSpentTime()).isEqualTo(2850L);
        assertThat(summoner.getPlayedSeasons()).containsExactlyInAnyOrder(13, 14, 15);
    }
}