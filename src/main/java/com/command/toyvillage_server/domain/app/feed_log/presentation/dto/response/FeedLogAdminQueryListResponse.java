package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FeedLogAdminQueryListResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FeedLogAdminQueryListResponse from(List<FeedLog> feedLogs) {
        return FeedLogAdminQueryListResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedLogId,
            String name,
            String animalKind,
            String animalName,
            String feedType,
            Float feedAmount,
            LocalDateTime feedDateTime
    ) {
        public static FeedLogResponse from(FeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedLogId(feedLog.getId())
                    .name(feedLog.getAppAdmin().getName())
                    .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                    .animalName(feedLog.getAnimalManage().getAnimalName())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedDateTime(feedLog.getFeedDateTime())
                    .build();
        }
    }
}
