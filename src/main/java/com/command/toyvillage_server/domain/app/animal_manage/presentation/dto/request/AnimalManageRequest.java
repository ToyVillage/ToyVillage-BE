package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalGender;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record AnimalManageRequest(
    @NotNull(message = "종을 선택해주세요.")
    Long animalKindId,

    @NotBlank(message = "개제명을 입력해주세요.")
    String animalName,

    @NotNull(message = "성별을 선택해주세요.")
    AnimalGender animalGender,

    @NotNull(message = "출생년도를 입력해주세요.")
    Integer birthYear,

    @Size(max = 255, message = "기타정보는 255자 이하여야 합니다.")
    String otherInfo,

    @NotBlank(message = "동물 사진을 포함해주세요.")
    String fileKey
) {

}
