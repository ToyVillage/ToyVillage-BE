package com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import lombok.Builder;

@Builder
public record FifthFloorFeedLogResponse(
        Long feedLogId,
        String animalKind,
        String animalName,
        boolean animalDeleted,
        String feedType,
        Float feedAmount,
        Unit feedUnit,
        String staffName
) {
    public static FifthFloorFeedLogResponse from(FifthFloorFeedLog feedLog) {
        return FifthFloorFeedLogResponse.builder()
                .feedLogId(feedLog.getId())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .animalDeleted(feedLog.getAnimalManage().isDeleteYn())
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedUnit(feedLog.getFeedUnit())
                .staffName(feedLog.getAppAdmin().getName())
                .build();
    }
}
