package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DashBoardQueryAnimalManageResponse(
        String title,
        LocalDateTime createdAt
) {
    public static DashBoardQueryAnimalManageResponse from(AnimalObservation animalObservation) {
        return DashBoardQueryAnimalManageResponse.builder()
                .title(animalObservation.getTitle())
                .createdAt(animalObservation.getCreatedAt())
                .build();
    }
}
