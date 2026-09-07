package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record AnimalKindQueryResponse(
    Long animalKindId,
    String kindName,
    String engName,
    String scientificName,
    AnimalTaxonomic animalTaxonomic,
    String detailKind,
    List<String> legalStatuses,
    long animalCount,
    FileResponse kindImage
) {
    public static AnimalKindQueryResponse of(
        AnimalKind animalKind,
        List<String> legalStatuses,
        long animalCount
    ) {
        return AnimalKindQueryResponse.builder()
            .animalKindId(animalKind.getId())
            .kindName(animalKind.getKindName())
            .engName(animalKind.getEngName())
            .scientificName(animalKind.getScientificName())
            .animalTaxonomic(animalKind.getAnimalTaxonomic())
            .detailKind(animalKind.getDetailKind())
            .legalStatuses(legalStatuses)
            .animalCount(animalCount)
            .kindImage(FileResponse.from(animalKind.getKindImage()))
            .build();
    }
}
