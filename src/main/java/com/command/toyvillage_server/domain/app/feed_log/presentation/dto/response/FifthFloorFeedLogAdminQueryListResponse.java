package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FifthFloorFeedLogAdminQueryListResponse(
        List<FeedLogResponse> feedLogs,
        int totalPageSize
) {
    public static FifthFloorFeedLogAdminQueryListResponse from(Page<FifthFloorFeedLog> feedLogs) {
        return FifthFloorFeedLogAdminQueryListResponse.builder()
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
            boolean animalDeleted,
            String feedType,
            Float feedAmount,
            Unit feedUnit,
            LocalDate feedDate
    ) {
        public static FeedLogResponse from(FifthFloorFeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedLogId(feedLog.getId())
                    .staffName(feedLog.getAppAdmin().getName())
                    .animalKind(feedLog.getAnimalKindName())
                    .animalName(feedLog.getAnimalName())
                    .animalDeleted(feedLog.isAnimalDeleted())
                    .feedType(feedLog.getFeedType())
                    .feedAmount(feedLog.getFeedAmount())
                    .feedUnit(feedLog.getFeedUnit())
                    .feedDate(feedLog.getFeedDate())
                    .build();
        }
    }
}
