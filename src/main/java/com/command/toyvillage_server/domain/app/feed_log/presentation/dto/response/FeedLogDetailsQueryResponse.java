package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record FeedLogDetailsQueryResponse(
        Long feedLogId,
        String feedType,
        Integer feedAmount,
        LocalDate feedDate,
        LocalDateTime feedStartTime,
        String significant
) {
    public static FeedLogDetailsQueryResponse from(FeedLog feedLog) {
        return FeedLogDetailsQueryResponse.builder()
                .feedLogId(feedLog.getId())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeed_amount())
                .feedDate(feedLog.getFeedDate())
                .feedStartTime(feedLog.getFeedStartTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
