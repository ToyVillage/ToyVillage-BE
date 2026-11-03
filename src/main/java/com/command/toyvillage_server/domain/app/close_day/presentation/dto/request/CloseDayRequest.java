package com.command.toyvillage_server.domain.app.close_day.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CloseDayRequest(
        @NotBlank(message = "휴관일 제목을 입력해주세요.")
        String title,

        @NotNull(message = "휴관 시작일을 입력해주세요.")
        LocalDate startCloseTime,

        @NotNull(message = "휴관 종료일을 입력해주세요.")
        LocalDate endCloseTime
) {
}
