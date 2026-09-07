package com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FeedLogRequest(
        LocalDate feedDate,
        LocalDateTime feedStartTime,
        LocalDateTime feedEndTime,
        String feedType,
        Integer feed_amount,
        Integer significant
) {
}
