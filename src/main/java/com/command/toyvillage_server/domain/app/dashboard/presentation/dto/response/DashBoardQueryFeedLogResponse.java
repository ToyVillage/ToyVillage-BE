package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DashBoardQueryFeedLogResponse(
        String animalKind,
        String animalName,
        LocalDateTime feedDateTime
) {
    public static DashBoardQueryFeedLogResponse from(FeedLog feedLog) {
        return DashBoardQueryFeedLogResponse.builder()
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .feedDateTime(feedLog.getFeedDateTime())
                .build();
    }
}
