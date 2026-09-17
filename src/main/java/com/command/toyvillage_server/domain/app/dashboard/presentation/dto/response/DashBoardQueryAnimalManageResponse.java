package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DashBoardQueryAnimalManageResponse(
        Long animalObservationId,
        String title,
        LocalDateTime createdAt
) {
    public static DashBoardQueryAnimalManageResponse from(AnimalObservation animalObservation) {
        return DashBoardQueryAnimalManageResponse.builder()
                .animalObservationId(animalObservation.getId())
                .title(animalObservation.getTitle())
                .createdAt(animalObservation.getCreatedAt())
                .build();
    }
}
