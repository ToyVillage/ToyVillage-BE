package com.command.toyvillage_server.domain.app.close_day.presentation.dto.response;

import com.command.toyvillage_server.domain.app.close_day.domain.CloseDay;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CloseDayResponse(
        Long id,
        String title,
        LocalDate startCloseTime,
        LocalDate endCloseTime
) {
    public static CloseDayResponse from(CloseDay closeDay) {
        return CloseDayResponse.builder()
                .id(closeDay.getId())
                .title(closeDay.getTitle())
                .startCloseTime(closeDay.getStartCloseTime())
                .endCloseTime(closeDay.getEndCloseTime())
                .build();
    }
}
