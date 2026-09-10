package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record FeedLogAdminSalaryHistoryListQueryResponse(
        Long feedLogId,
        String name,
        String feedType,
        Integer feedAmount,
        LocalDate feedDate,
        LocalDateTime feedStartTime,
        String significant
) {
    public static FeedLogAdminSalaryHistoryListQueryResponse from(FeedLog feedLog) {
        return FeedLogAdminSalaryHistoryListQueryResponse.builder()
                .feedLogId(feedLog.getId())
                .name(feedLog.getAppAdmin().getName())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeed_amount())
                .feedDate(feedLog.getFeedDate())
                .feedStartTime(feedLog.getFeedStartTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
