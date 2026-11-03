package com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogAllResponse(
        Long feedLogId,
        String animalKind,
        String animalName,
        boolean animalDeleted,
        LocalDateTime feedDateTime,
        String feedType,
        Float feedAmount,
        Float remainingAmount,
        Unit feedUnit,
        String significant,
        String staffName
) {
    public static FeedLogAllResponse from(FeedLog feedLog) {
        return FeedLogAllResponse.builder()
                .feedLogId(feedLog.getId())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .animalDeleted(feedLog.getAnimalManage().isDeleteYn())
                .feedDateTime(feedLog.getFeedDateTime())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .remainingAmount(feedLog.getRemainingAmount())
                .feedUnit(feedLog.getFeedUnit())
                .significant(feedLog.getSignificant())
                .staffName(feedLog.getAppAdmin().getName())
                .build();
    }
}
