package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FifthFloorFeedLogAdminDetailsQueryResponse(
        Long animalId,
        String staffName,
        String animalKind,
        String animalName,
        boolean animalDeleted,
        FileResponse animalImageUrl,
        String feedType,
        Float feedAmount,
        Unit feedUnit,
        LocalDate feedDate
) {
    public static FifthFloorFeedLogAdminDetailsQueryResponse from(FifthFloorFeedLog feedLog) {
        return FifthFloorFeedLogAdminDetailsQueryResponse.builder()
                .animalId(feedLog.getAnimalManage().getId())
                .staffName(feedLog.getAppAdmin().getName())
                .animalKind(feedLog.getAnimalManage().getAnimalKind().getKindName())
                .animalName(feedLog.getAnimalManage().getAnimalName())
                .animalDeleted(feedLog.getAnimalManage().isDeleteYn())
                .animalImageUrl(FileResponse.from(feedLog.getAnimalManage().getAnimalImage()))
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedUnit(feedLog.getFeedUnit())
                .feedDate(feedLog.getFeedDate())
                .build();
    }
}
