package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record FeedLogDetailsQueryResponse(
        Long id,
        String feedType,
        Integer feed_amount,
        LocalDate feedDate,
        LocalDateTime feedStartTime,
        Integer significant
) {
    public static FeedLogDetailsQueryResponse from(FeedLog feedLog) {
        return FeedLogDetailsQueryResponse.builder()
                .id(feedLog.getId())
                .feedType(feedLog.getFeedType())
                .feed_amount(feedLog.getFeed_amount())
                .feedDate(feedLog.getFeedDate())
                .feedStartTime(feedLog.getFeedStartTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
