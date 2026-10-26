package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DashBoardQueryFeedLogResponse(
        Long feedLogId,
        String animalKind,
        String animalName,
        boolean animalDeleted,
        LocalDateTime feedDateTime
) {
    public static DashBoardQueryFeedLogResponse from(FeedLog feedLog) {
        return DashBoardQueryFeedLogResponse.builder()
                .feedLogId(feedLog.getId())
                .animalKind(feedLog.getAnimalKindName())
                .animalName(feedLog.getAnimalName())
                .animalDeleted(feedLog.isAnimalDeleted())
                .feedDateTime(feedLog.getFeedDateTime())
                .build();
    }
}
