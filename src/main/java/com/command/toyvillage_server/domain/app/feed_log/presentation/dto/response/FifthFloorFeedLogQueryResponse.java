package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FifthFloorFeedLogQueryResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FifthFloorFeedLogQueryResponse from(List<FifthFloorFeedLog> feedLogs) {
        return FifthFloorFeedLogQueryResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedLogId,
            Long animalId,
            String feedType,
            Float feedAmount,
            Unit feedUnit,
            LocalDate feedDate
    ) {
        public static FeedLogResponse from(FifthFloorFeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedLogId(feedLog.getId())
                    .animalId(feedLog.getAnimalManage().getId())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedUnit(feedLog.getFeedUnit())
                    .feedDate(feedLog.getFeedDate())
                    .build();
        }
    }
}
