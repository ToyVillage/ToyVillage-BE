package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogAdminDetailsQueryResponse(
        Long animalId,
        String name,
        String animalKind,
        String animalName,
        String feedType,
        Float feedAmount,
        LocalDateTime feedDateTime,
        String significant
) {
    public static FeedLogAdminDetailsQueryResponse from(FeedLog feedLog) {
        return FeedLogAdminDetailsQueryResponse.builder()
                .animalId(feedLog.getAnimalManage().getId())
                .name(feedLog.getAppAdmin().getName())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedDateTime(feedLog.getFeedDateTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
