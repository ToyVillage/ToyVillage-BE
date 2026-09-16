package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
public record FeedLogQueryResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FeedLogQueryResponse from(List<FeedLog> feedLogs) {
        return FeedLogQueryResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long animalId,
            String feedType,
            Float feedAmount,
            OffsetDateTime feedDateTime,
            String significant
    ) {
        public static FeedLogResponse from(FeedLog feedLog) {
            return FeedLogResponse.builder()
                    .animalId(feedLog.getAnimalManage().getId())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedDateTime(feedLog.getFeedDateTimeKst())
                    .significant(feedLog.getSignificant())
                    .build();
        }
    }
}
