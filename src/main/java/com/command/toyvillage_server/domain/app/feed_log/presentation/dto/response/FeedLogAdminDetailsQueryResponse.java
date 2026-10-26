package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FeedLogAdminDetailsQueryResponse(
        Long animalId,
        String staffName,
        String animalKind,
        String animalName,
        boolean animalDeleted,
        FileResponse animalImageUrl,
        String feedType,
        Float feedAmount,
        Float remainingAmount,
        Unit feedUnit,
        LocalDateTime feedDateTime,
        String significant
) {
    public static FeedLogAdminDetailsQueryResponse from(FeedLog feedLog) {
        AnimalManage animalManage = feedLog.getAnimalManage();

        return FeedLogAdminDetailsQueryResponse.builder()
                .animalId(animalManage == null ? null : animalManage.getId())
                .staffName(feedLog.getAppAdmin().getName())
                .animalKind(feedLog.getAnimalKindName())
                .animalName(feedLog.getAnimalName())
                .animalDeleted(feedLog.isAnimalDeleted())
                .animalImageUrl(animalManage == null ? null : FileResponse.from(animalManage.getAnimalImage()))
                .feedType(feedLog.getFeedType())
                .feedAmount(feedLog.getFeedAmount())
                .remainingAmount(feedLog.getRemainingAmount())
                .feedUnit(feedLog.getFeedUnit())
                .feedDateTime(feedLog.getFeedDateTime())
                .significant(feedLog.getSignificant())
                .build();
    }
}
