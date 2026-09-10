package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FeedLogRequest(
        @NotNull(message = "급여 날짜를 입력해주세요.")
        LocalDate feedDate,
        @NotNull(message = "급여 시작시간을 입력해주세요.")
        LocalDateTime feedStartTime,
        @NotBlank(message = "급여 종류를 입력해주세요.")
        String feedType,
        @PositiveOrZero(message = "급여량은 0 이상이어야 합니다.")
        Integer feed_amount,
        String significant
) {
}
