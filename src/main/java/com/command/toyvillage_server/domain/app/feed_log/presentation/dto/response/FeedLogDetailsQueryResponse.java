package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogDetailsQueryResponse(
        Long feedLogId,
        String feedType,
        Float feedAmount,
        LocalDateTime feedDateTime,
        String significant
) {
    public static FeedLogDetailsQueryResponse from(FeedLog feedLog) {
        return FeedLogDetailsQueryResponse.builder()
                .feedLogId(feedLog.getId())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedDateTime(feedLog.getFeedDateTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
