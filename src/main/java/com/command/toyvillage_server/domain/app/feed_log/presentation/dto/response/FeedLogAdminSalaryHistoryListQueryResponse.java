package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FeedLogAdminSalaryHistoryListQueryResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FeedLogAdminSalaryHistoryListQueryResponse from(List<FeedLog> feedLogs) {
        return FeedLogAdminSalaryHistoryListQueryResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedLogId,
            String name,
            String feedType,
            Integer feedAmount,
            LocalDateTime feedDateTime,
            String significant
    ) {
        public static FeedLogResponse from(FeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedLogId(feedLog.getId())
                    .name(feedLog.getAppAdmin().getName())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedDateTime(feedLog.getFeedDateTime())
                    .significant(feedLog.getSignificant())
                    .build();
        }
    }
}
