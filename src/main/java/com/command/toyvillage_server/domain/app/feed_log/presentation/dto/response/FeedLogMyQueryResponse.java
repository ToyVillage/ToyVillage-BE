package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import lombok.Builder;

@Builder
public record FeedLogMyQueryResponse(
        Long feedId,
        String animalKind,
        String animalName
) {
    public static FeedLogMyQueryResponse from(FeedLog feedLog) {
        return FeedLogMyQueryResponse.builder()
                .feedId(feedLog.getId())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .build();
    }
}
