package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedLogMyQueryResponse(
        List<FeedLogResponse> feedLogs
) {
    public static FeedLogMyQueryResponse from(List<FeedLog> feedLogs) {
        return FeedLogMyQueryResponse.builder()
                .feedLogs(feedLogs.stream().map(FeedLogResponse::from).toList())
                .build();
    }

    @Builder
    private record FeedLogResponse(
            Long feedId,
            String animalKind,
            String animalName
    ) {
        public static FeedLogResponse from(FeedLog feedLog) {
            return FeedLogResponse.builder()
                    .feedId(feedLog.getId())
                    .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                    .animalName(feedLog.getAnimalManage().getAnimalName())
                    .build();
        }
    }
}
