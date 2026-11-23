package com.krazytop.leagueoflegends.infrastructure.adapter.out.common;

import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import com.krazytop.leagueoflegends.domain.model.match.Queue;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@UtilityClass
public class QueueMappingHelper {

    private static final Map<EnumQueue, List<GameQueueType>> QUEUE_MAPPING = Map.ofEntries(
            Map.entry(EnumQueue.NORMAL, List.of(
                    GameQueueType.TEAM_BUILDER_DRAFT_UNRANKED_5X5,
                    GameQueueType.NORMAL_5V5_BLIND_PICK,
                    GameQueueType.SWIFTPLAY,
                    GameQueueType.QUICKPLAY_NORMAL,
                    GameQueueType.NORMAL_5X5_DRAFT,
                    GameQueueType.NORMAL_5X5_BLIND_PICK_OLD
            )),
            Map.entry(EnumQueue.SOLO_RANKED, List.of(
                    GameQueueType.TEAM_BUILDER_RANKED_SOLO,
                    GameQueueType.RANKED_SOLO_5X5,
                    GameQueueType.TEAM_BUILDER_DRAFT_RANKED_5X5
            )),
            Map.entry(EnumQueue.FLEX_RANKED, List.of(GameQueueType.RANKED_FLEX_SR)),
            Map.entry(EnumQueue.ARAM, List.of(
                    GameQueueType.ARAM,
                    GameQueueType.ARAM_5X5,
                    GameQueueType.ARAM_5X5_OLD,
                    GameQueueType.BILGEWATER_ARAM_5X5,
                    GameQueueType.KIWI,
                    GameQueueType.KIWI_JADE
            )),
            Map.entry(EnumQueue.URF, List.of(
                    GameQueueType.URF,
                    GameQueueType.URF_5X5,
                    GameQueueType.URF_1V1,
                    GameQueueType.URF_2V2,
                    GameQueueType.URF_3V3,
                    GameQueueType.URF_4V4
            )),
            Map.entry(EnumQueue.ARURF, List.of(
                    GameQueueType.ALL_RANDOM_URF,
                    GameQueueType.SNOW_BATTLE_ARURF
            )),
            Map.entry(EnumQueue.NEXUS_BLITZ, List.of(GameQueueType.NEXUS_BLITZ)),
            Map.entry(EnumQueue.ONE_FOR_ALL, List.of(
                    GameQueueType.ONEFORALL_5X5,
                    GameQueueType.ONE_FOR_ALL_MIRROR
            )),
            Map.entry(EnumQueue.ULTIMATE_SPELLBOOK, List.of(GameQueueType.ULTBOOK)),
            Map.entry(EnumQueue.ARENA, List.of(GameQueueType.CHERRY)),
            Map.entry(EnumQueue.DOOM_BOTS, List.of(
                    GameQueueType.DOOMBOTS_V2,
                    GameQueueType.NIGHTMARE_BOT_5X5,
                    GameQueueType.NIGHTMARE_BOT_5X5_VOTE,
                    GameQueueType.NIGHTMARE_BOT_5X5_RANK1,
                    GameQueueType.NIGHTMARE_BOT_5X5_RANK2,
                    GameQueueType.NIGHTMARE_BOT_5X5_RANK5
            )),
            Map.entry(EnumQueue.CLASH, List.of(
                    GameQueueType.CLASH,
                    GameQueueType.ARAM_CLASH,
                    GameQueueType.ARURF_CLASH
            )),
            Map.entry(EnumQueue.BRAWL, List.of(GameQueueType.BRAWL)),
            Map.entry(EnumQueue.SWARM, List.of(GameQueueType.STRAWBERRY))
    );

    public static List<String> resolveQueueIds(EnumQueue queueType) {
        if (queueType == null) return List.of();
        return QUEUE_MAPPING.getOrDefault(queueType, List.of()).stream()
                .flatMap(type -> Arrays.stream(type.getValues()))
                .map(String::valueOf)
                .toList();
    }

    public static Queue toQueue(int queueId) {
        EnumQueue type = GameQueueType.getFromId(queueId)
                .flatMap(r4jType -> QUEUE_MAPPING.entrySet().stream()
                        .filter(entry -> entry.getValue().contains(r4jType))
                        .map(Map.Entry::getKey)
                        .findFirst())
                .orElseGet(() -> {
                    log.warn("La queue {} n'est pas encore prise en charge", queueId);
                    return null;
                });

        return new Queue()
                .setId(String.valueOf(queueId))
                .setType(type);
    }
}