package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogAdminSalaryHistoryListQueryResponse(
        Long feedLogId,
        String name,
        String feedType,
        Integer feedAmount,
        LocalDateTime feedDateTime,
        String significant
) {
    public static FeedLogAdminSalaryHistoryListQueryResponse from(FeedLog feedLog) {
        return FeedLogAdminSalaryHistoryListQueryResponse.builder()
                .feedLogId(feedLog.getId())
                .name(feedLog.getAppAdmin().getName())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeed_amount())
                .feedDateTime(feedLog.getFeedDateTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
