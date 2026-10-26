package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request;

import com.command.toyvillage_server.domain.app.feed_log.domain.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record FifthFloorFeedLogRequest(
        @NotNull(message = "급여 날짜를 입력해주세요.")
        LocalDate feedDate,
        @NotBlank(message = "먹이 종류를 입력해주세요.")
        String feedType,
        @NotNull(message = "급여량을 입력해주세요.")
        @PositiveOrZero(message = "급여량은 0 이상이어야 합니다.")
        Float feedAmount,
        @NotNull(message = "급여 단위를 입력해주세요.")
        Unit feedUnit
) {
}
