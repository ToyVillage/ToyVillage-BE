package com.command.toyvillage_server.domain.app.open_time.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record OpenTimeRequest(
        @NotNull(message = "운영 날짜를 입력해주세요.")
        LocalDate openDate,

        @NotNull(message = "운영 시작시간을 입력해주세요.")
        LocalTime startOpenTime,

        @NotNull(message = "운영 종료시간을 입력해주세요.")
        LocalTime endOpenTime
) {
}
