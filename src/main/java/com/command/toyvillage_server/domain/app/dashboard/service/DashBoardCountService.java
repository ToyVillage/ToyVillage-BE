package com.command.toyvillage_server.domain.app.dashboard.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardCountResponse;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class DashBoardCountService {
    private final FeedLogRepository feedLogRepository;
    private final AnimalKindRepository animalKindRepository;
    private final WorkReportRepository workReportRepository;
    private final WorkLogRepository workLogRepository;

    @Transactional(readOnly = true)
    public DashBoardCountResponse execute() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = startOfWeek.plusWeeks(1).atStartOfDay();

        return DashBoardCountResponse.of(
                feedLogRepository.countByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThan(
                        startDateTime,
                        endDateTime
                ),
                animalKindRepository.countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        startDateTime,
                        endDateTime
                ),
                workReportRepository.countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        startDateTime,
                        endDateTime
                ),
                workLogRepository.countByWriteAtGreaterThanEqualAndWriteAtLessThan(
                        startOfWeek,
                        startOfWeek.plusWeeks(1)
                )
        );
    }
}
