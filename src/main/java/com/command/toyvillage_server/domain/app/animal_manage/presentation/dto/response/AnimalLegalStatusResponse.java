package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import lombok.Builder;

@Builder
public record AnimalLegalStatusResponse(
    Long animalLegalStatusId,
    String kind
) {
    public static AnimalLegalStatusResponse from(AnimalLegalStatus animalLegalStatus) {
        return AnimalLegalStatusResponse.builder()
            .animalLegalStatusId(animalLegalStatus.getId())
            .kind(animalLegalStatus.getKind())
            .build();
    }
}
