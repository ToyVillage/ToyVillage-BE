package com.command.toyvillage_server.domain.app.dashboard.service;

import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardOverallOperationsResponse;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class DashBoardOverallOperationsService {
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public DashBoardOverallOperationsResponse execute() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = startOfWeek.plusWeeks(1).atStartOfDay();

        return DashBoardOverallOperationsResponse.of(
                taskRepository.countInProgressCreatedBetween(
                        startDateTime,
                        endDateTime,
                        today
                ),
                taskRepository.countCompletedCreatedBetween(startDateTime, endDateTime),
                taskRepository.countExpiredCreatedBetween(
                        startDateTime,
                        endDateTime,
                        today
                )
        );
    }
}
