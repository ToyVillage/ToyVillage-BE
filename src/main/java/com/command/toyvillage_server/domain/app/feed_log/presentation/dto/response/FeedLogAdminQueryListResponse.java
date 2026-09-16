package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FeedLogAdminQueryListResponse(
        List<FeedLogResponse> feedLogs,
        int totalPageSize
) {
    public static FeedLogAdminQueryListResponse from(Page<FeedLog> feedLogs) {
        return FeedLogAdminQueryListResponse.builder()
                .feedLogs(feedLogs.map(FeedLogResponse::from).toList())
                .totalPageSize(feedLogs.getTotalPages())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedLogId,
            String staffName,
            String animalKind,
            String animalName,
            String feedType,
            Float feedAmount,
            LocalDateTime feedDateTime
    ) {
        public static FeedLogResponse from(FeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedLogId(feedLog.getId())
                    .staffName(feedLog.getAppAdmin().getName())
                    .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                    .animalName(feedLog.getAnimalManage().getAnimalName())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedDateTime(feedLog.getFeedDateTime())
                    .build();
        }
    }
}
