package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FifthFloorFeedLogAdminSalaryHistoryListQueryResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FifthFloorFeedLogAdminSalaryHistoryListQueryResponse from(List<FifthFloorFeedLog> feedLogs) {
        return FifthFloorFeedLogAdminSalaryHistoryListQueryResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedLogId,
            String staffName,
            String feedType,
            Float feedAmount,
            Unit feedUnit,
            LocalDate feedDate
    ) {
        public static FeedLogResponse from(FifthFloorFeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedLogId(feedLog.getId())
                    .staffName(feedLog.getAppAdmin().getName())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedUnit(feedLog.getFeedUnit())
                    .feedDate(feedLog.getFeedDate())
                    .build();
        }
    }
}
