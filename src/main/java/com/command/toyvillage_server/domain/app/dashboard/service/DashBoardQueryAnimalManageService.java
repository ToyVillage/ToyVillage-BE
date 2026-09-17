package com.command.toyvillage_server.domain.app.dashboard.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardQueryAnimalManageResponse;
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
public class DashBoardQueryAnimalManageService {
    private final AnimalObservationRepository animalObservationRepository;

    @Transactional(readOnly = true)
    public Page<DashBoardQueryAnimalManageResponse> execute(Pageable pageable) {
        LocalDate startOfWeek = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = startOfWeek.atStartOfDay();
        LocalDateTime endDateTime = startOfWeek.plusWeeks(1).atStartOfDay();

        return animalObservationRepository
                .findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        startDateTime,
                        endDateTime,
                        pageable
                )
                .map(DashBoardQueryAnimalManageResponse::from);
    }
}
