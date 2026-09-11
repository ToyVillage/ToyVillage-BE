package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AnimalObservationRequest(
    @NotBlank(message = "관찰 및 특이사항 제목을 입력해주세요.")
    @Size(max = 100, message = "관찰 및 특이사항 제목은 100자 이하로 입력해주세요.")
    String title,

    @NotBlank(message = "관찰 및 특이사항 내용을 입력해주세요.")
    @Size(max = 2000, message = "관찰 및 특이사항 내용은 2000자 이하로 입력해주세요.")
    String content,

    List<String> fileKeys
) {
}
