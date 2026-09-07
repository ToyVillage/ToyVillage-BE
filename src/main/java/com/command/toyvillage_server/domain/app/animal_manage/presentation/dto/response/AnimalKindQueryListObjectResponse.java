package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

@Builder
public record AnimalKindQueryListObjectResponse(
    Long animalKindId,
    AnimalTaxonomic animalTaxonomic,
    String kindName,
    String scientificName,
    long animalCount,
    FileResponse kindImage
) {
    public static AnimalKindQueryListObjectResponse of(AnimalKind animalKind, long animalCount) {
        return AnimalKindQueryListObjectResponse.builder()
            .animalKindId(animalKind.getId())
            .animalTaxonomic(animalKind.getAnimalTaxonomic())
            .kindName(animalKind.getKindName())
            .scientificName(animalKind.getScientificName())
            .animalCount(animalCount)
            .kindImage(FileResponse.from(animalKind.getKindImage()))
            .build();
    }
}
