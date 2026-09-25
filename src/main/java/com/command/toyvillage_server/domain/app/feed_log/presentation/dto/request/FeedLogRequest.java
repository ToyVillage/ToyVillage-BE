package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record FeedLogRequest(
        @NotNull(message = "급여 날짜와 시간을 입력해주세요.")
        LocalDateTime feedDateTime,
        @NotBlank(message = "급여 종류를 입력해주세요.")
        String feedType,
        @NotNull(message = "급여량을 입력해주세요.")
        @PositiveOrZero(message = "급여량은 0 이상이어야 합니다.")
        Float feedAmount,
        @NotNull(message = "특이사항을 입력해주세요.")
        String significant
) {
}
