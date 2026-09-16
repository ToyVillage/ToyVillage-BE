package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AnimalKindRequest(
    @NotBlank(message = "국명을 입력해주세요.")
    String animalName,

    @NotBlank(message = "영문명을 입력해주세요.")
    String animalEngName,

    @NotBlank(message = "학명을 입력해주세요.")
    String animalScientificName,

    @NotNull(message = "분류군을 선택해주세요.")
    AnimalTaxonomic animalTaxonomic,

    String animalDetailKind,

    @NotBlank(message = "종 사진을 포함해주세요.")
    String fileKey,

    List<@NotNull(message = "법정지정분류를 선택해주세요.") Long> animalLegalDesignation
) {
}
