package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalGender;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record AnimalManageQueryResponse(
    Long animalManageId,
    String animalName,
    AnimalGender animalGender,
    int birthYear,
    String otherInfo,
    FileResponse animalImage,
    Long animalKindId,
    String kindName,
    String scientificName,
    AnimalTaxonomic animalTaxonomic,
    String detailKind,
    List<String> legalStatuses
) {
    public static AnimalManageQueryResponse of(AnimalManage animalManage, List<String> legalStatuses) {
        AnimalKind animalKind = animalManage.getAnimalKind();

        return AnimalManageQueryResponse.builder()
            .animalManageId(animalManage.getId())
            .animalName(animalManage.getAnimalName())
            .animalGender(animalManage.getAnimalGender())
            .birthYear(animalManage.getBirthYear())
            .otherInfo(animalManage.getOtherInfo())
            .animalImage(FileResponse.from(animalManage.getAnimalImage()))
            .animalKindId(animalKind.getId())
            .kindName(animalKind.getKindName())
            .scientificName(animalKind.getScientificName())
            .animalTaxonomic(animalKind.getAnimalTaxonomic())
            .detailKind(animalKind.getDetailKind())
            .legalStatuses(legalStatuses)
            .build();
    }
}
