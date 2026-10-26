package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import lombok.Builder;

import java.util.List;

@Builder
public record FifthFloorFeedLogMyQueryResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FifthFloorFeedLogMyQueryResponse from(List<FifthFloorFeedLog> feedLogs) {
        return FifthFloorFeedLogMyQueryResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedId,
            String animalKind,
            String animalName,
            boolean animalDeleted
    ) {
        public static FeedLogResponse from(FifthFloorFeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedId(feedLog.getId())
                    .animalKind(feedLog.getAnimalKindName())
                    .animalName(feedLog.getAnimalName())
                    .animalDeleted(feedLog.isAnimalDeleted())
                    .build();
        }
    }
}
