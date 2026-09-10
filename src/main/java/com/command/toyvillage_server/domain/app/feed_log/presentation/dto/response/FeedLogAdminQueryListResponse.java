package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogAdminQueryListResponse(
        Long feedLogId,
        String name,
        String animalKind,
        String animalName,
        String feedType,
        Integer feedAmount,
        LocalDateTime feedDateTime
) {
    public static FeedLogAdminQueryListResponse from(FeedLog feedLog) {
        return FeedLogAdminQueryListResponse.builder()
                .feedLogId(feedLog.getId())
                .name(feedLog.getAppAdmin().getName())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeed_amount())
                .feedDateTime(feedLog.getFeedDateTime())
                .build();
    }
}
