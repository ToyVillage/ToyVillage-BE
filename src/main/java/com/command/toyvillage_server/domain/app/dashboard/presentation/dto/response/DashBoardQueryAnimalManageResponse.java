package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DashBoardQueryAnimalManageResponse(
        Long animalObservationId,
        Long animalId,
        String title,
        LocalDateTime createdAt
) {
    public static DashBoardQueryAnimalManageResponse of(AnimalObservation animalObservation, AnimalManage animalManage) {
        return DashBoardQueryAnimalManageResponse.builder()
                .animalObservationId(animalObservation.getId())
                .animalId(animalManage.getId())
                .title(animalObservation.getTitle())
                .createdAt(animalObservation.getCreatedAt())
                .build();
    }
}
