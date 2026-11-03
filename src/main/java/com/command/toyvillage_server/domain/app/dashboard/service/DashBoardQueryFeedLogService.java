package com.command.toyvillage_server.domain.app.dashboard.service;

import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardQueryFeedLogResponse;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class DashBoardQueryFeedLogService {
    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public Page<DashBoardQueryFeedLogResponse> execute(Pageable pageable) {
        LocalDate startOfWeek = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = startOfWeek.plusWeeks(1).atStartOfDay();

        return feedLogRepository.findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThan(
                        startDateTime,
                        endDateTime,
                        pageable
                )
                .map(DashBoardQueryFeedLogResponse::from);
    }
}
