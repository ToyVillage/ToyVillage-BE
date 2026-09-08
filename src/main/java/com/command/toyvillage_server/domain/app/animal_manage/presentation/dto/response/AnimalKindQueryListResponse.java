package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record AnimalKindQueryListResponse(
    List<AnimalKindQueryListObjectResponse> animalKinds,
    int totalPageSize
) {
    public static AnimalKindQueryListResponse from(Page<AnimalKindQueryListObjectResponse> animalKinds) {
        return AnimalKindQueryListResponse.builder()
            .animalKinds(animalKinds.toList())
            .totalPageSize(animalKinds.getTotalPages())
            .build();
    }
}
