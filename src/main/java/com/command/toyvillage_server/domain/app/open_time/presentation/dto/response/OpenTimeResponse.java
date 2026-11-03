package com.command.toyvillage_server.domain.app.open_time.presentation.dto.response;

import com.command.toyvillage_server.domain.app.open_time.domain.OpenTime;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record OpenTimeResponse(
        Long id,
        LocalDate openDate,
        LocalTime startOpenTime,
        LocalTime endOpenTime
) {
    private static final LocalTime DEFAULT_START_OPEN_TIME = LocalTime.of(11, 0);
    private static final LocalTime DEFAULT_END_OPEN_TIME = LocalTime.of(18, 0);

    public static OpenTimeResponse from(OpenTime openTime) {
        return OpenTimeResponse.builder()
                .id(openTime.getId())
                .openDate(openTime.getOpenDate())
                .startOpenTime(openTime.getStartOpenTime())
                .endOpenTime(openTime.getEndOpenTime())
                .build();
    }

    public static OpenTimeResponse defaultFor(LocalDate openDate) {
        return OpenTimeResponse.builder()
                .openDate(openDate)
                .startOpenTime(DEFAULT_START_OPEN_TIME)
                .endOpenTime(DEFAULT_END_OPEN_TIME)
                .build();
    }
}
