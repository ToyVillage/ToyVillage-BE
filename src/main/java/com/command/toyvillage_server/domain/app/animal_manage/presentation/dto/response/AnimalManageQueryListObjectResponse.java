package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalGender;
import lombok.Builder;

@Builder
public record AnimalManageQueryListObjectResponse(
    Long animalManageId,
    String animalName,
    AnimalGender animalGender,
    int birthYear
) {
    public static AnimalManageQueryListObjectResponse from(AnimalManage animalManage) {
        return AnimalManageQueryListObjectResponse.builder()
            .animalManageId(animalManage.getId())
            .animalName(animalManage.getAnimalName())
            .animalGender(animalManage.getAnimalGender())
            .birthYear(animalManage.getBirthYear())
            .build();
    }
}
