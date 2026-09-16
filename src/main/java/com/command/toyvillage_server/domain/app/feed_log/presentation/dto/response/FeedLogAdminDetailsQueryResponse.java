package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record FeedLogAdminDetailsQueryResponse(
        Long animalId,
        String staffName,
        String animalKind,
        String animalName,
        FileResponse animalImageUrl,
        String feedType,
        Float feedAmount,
        OffsetDateTime feedDateTime,
        String significant
) {
    public static FeedLogAdminDetailsQueryResponse from(FeedLog feedLog) {
        return FeedLogAdminDetailsQueryResponse.builder()
                .animalId(feedLog.getAnimalManage().getId())
                .staffName(feedLog.getAppAdmin().getName())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .animalImageUrl(FileResponse.from(feedLog.getAnimalManage().getAnimalImage()))
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedDateTime(feedLog.getFeedDateTimeKst())
                .significant(feedLog.getSignificant())
                .build();
    }
}
