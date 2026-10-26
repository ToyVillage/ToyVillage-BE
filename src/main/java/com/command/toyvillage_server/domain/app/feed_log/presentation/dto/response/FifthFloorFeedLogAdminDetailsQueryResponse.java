package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
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
        AnimalManage animalManage = feedLog.getAnimalManage();

        return FifthFloorFeedLogAdminDetailsQueryResponse.builder()
                .animalId(animalManage == null ? null : animalManage.getId())
                .staffName(feedLog.getAppAdmin().getName())
                .animalKind(feedLog.getAnimalKindName())
                .animalName(feedLog.getAnimalName())
                .animalDeleted(feedLog.isAnimalDeleted())
                .animalImageUrl(animalManage == null ? null : FileResponse.from(animalManage.getAnimalImage()))
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .feedUnit(feedLog.getFeedUnit())
                .feedDate(feedLog.getFeedDate())
                .build();
    }
}
