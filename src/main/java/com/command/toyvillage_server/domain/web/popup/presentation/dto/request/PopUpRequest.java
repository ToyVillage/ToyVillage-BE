package com.command.toyvillage_server.domain.web.popup.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PopUpRequest(

        @NotBlank(message = "팝업 이미지를 선택해주세요.")
        @JsonProperty("file_key")
        String fileKey,

        @NotNull(message = "팝업 마감기한을 설정해주세요.")
        @JsonProperty("expiration_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate expirationDate,

        @NotNull(message = "팝업의 우선순위를 선택해주세요.")
        Integer priority
) {
}
