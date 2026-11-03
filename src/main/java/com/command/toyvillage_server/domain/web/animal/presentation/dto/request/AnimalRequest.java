package com.command.toyvillage_server.domain.web.animal.presentation.dto.request;

import com.command.toyvillage_server.domain.web.animal.domain.enums.AnimalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AnimalRequest(

        @NotBlank(message = "동물 이름을 비워둘 수 없습니다.")
        String animalKind,

        @NotNull(message = "동물 사진을 첨부해주세요.")
        MultipartFile  animalImage,

        @NotBlank(message = "동물 소개를 비워둘 수 없습니다.")
        String animalDescription,

        @NotNull(message = "동물 종류를 비워둘 수 없습니다.")
        AnimalType animalType,

        @NotNull(message = "인기 동물 여부를 선택해주세요.")
        Boolean popularAnimal
) {
}
