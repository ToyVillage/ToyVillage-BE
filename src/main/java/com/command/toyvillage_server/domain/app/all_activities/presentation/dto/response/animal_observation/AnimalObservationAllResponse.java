package com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.animal_observation;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AnimalObservationAllResponse(
        Long animalObservationId,
        Long animalManageId,
        String animalKind,
        String animalName,
        boolean animalDeleted,
        LocalDateTime createdAt,
        String title,
        String authorName,
        long fileCount
) {
    public static AnimalObservationAllResponse from(AnimalObservation observation, long fileCount) {
        return AnimalObservationAllResponse.builder()
                .animalObservationId(observation.getId())
                .animalManageId(observation.getAnimalManage().getId())
                .animalKind(observation.getAnimalManage().getAnimalKind().getKindName())
                .animalName(observation.getAnimalManage().getAnimalName())
                .animalDeleted(observation.getAnimalManage().isDeleteYn())
                .createdAt(observation.getCreatedAt())
                .title(observation.getTitle())
                .authorName(observation.getAuthor().getName())
                .fileCount(fileCount)
                .build();
    }
}
