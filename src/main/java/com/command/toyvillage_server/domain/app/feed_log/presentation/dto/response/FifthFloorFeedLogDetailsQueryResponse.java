package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FifthFloorFeedLogDetailsQueryResponse(
        Long feedLogId,
        String feedType,
        Float feedAmount,
        Unit feedUnit,
        LocalDateTime feedDate
) {
    public static FifthFloorFeedLogDetailsQueryResponse from(FifthFloorFeedLog feedLog) {
        return FifthFloorFeedLogDetailsQueryResponse.builder()
                .feedLogId(feedLog.getId())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedUnit(feedLog.getFeedUnit())
                .feedDate(feedLog.getFeedDate())
                .build();
    }
}
