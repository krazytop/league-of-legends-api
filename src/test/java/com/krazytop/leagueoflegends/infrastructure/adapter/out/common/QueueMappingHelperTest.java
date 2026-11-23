package com.krazytop.leagueoflegends.infrastructure.adapter.out.common;

import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import com.krazytop.leagueoflegends.domain.model.match.Queue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QueueMappingHelperTest {

    @ParameterizedTest
    @EnumSource(EnumQueue.class)
    void resolveQueueIds_shouldReturnNonEmptyListForEveryEnumQueue(EnumQueue queueType) {
        // Act
        List<String> ids = QueueMappingHelper.resolveQueueIds(queueType);

        // Assert
        assertThat(ids).isNotEmpty();
    }

    @Test
    void resolveQueueIds_withNull_shouldReturnEmptyList() {
        // Act
        List<String> ids = QueueMappingHelper.resolveQueueIds(null);

        // Assert
        assertThat(ids).isEmpty();
    }

    @Test
    void toQueue_withKnownQueueId_shouldReturnQueueWithMappedType() {
        // Act
        Queue result = QueueMappingHelper.toQueue(420);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("420");
        assertThat(result.getType()).isEqualTo(EnumQueue.SOLO_RANKED);
    }

    @Test
    void toQueue_withUnknownQueueId_shouldReturnQueueWithNullType() {
        // Act
        Queue result = QueueMappingHelper.toQueue(999999);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("999999");
        assertThat(result.getType()).isNull();
    }
}