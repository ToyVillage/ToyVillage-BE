package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogQueryResponse(
        Long animalId,
        String feedType,
        Integer feedAmount,
        LocalDateTime feedDateTime,
        String significant
) {
    public static FeedLogQueryResponse from(FeedLog feedLog) {
        return FeedLogQueryResponse.builder()
                .animalId(feedLog.getAnimalManage().getId())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeed_amount())
                .feedDateTime(feedLog.getFeedDateTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
