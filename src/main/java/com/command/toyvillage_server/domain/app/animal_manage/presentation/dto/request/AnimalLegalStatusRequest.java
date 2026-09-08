package com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AnimalLegalStatusRequest(
    @NotBlank(message = "법정지정분류를 입력해주세요.")
    String kind
) {
}
