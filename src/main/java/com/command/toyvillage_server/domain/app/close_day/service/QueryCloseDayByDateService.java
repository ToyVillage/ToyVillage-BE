package com.command.toyvillage_server.domain.app.close_day.service;

import com.command.toyvillage_server.domain.app.close_day.domain.repository.CloseDayRepository;
import com.command.toyvillage_server.domain.app.close_day.presentation.dto.response.CloseDayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryCloseDayByDateService {
    private final CloseDayRepository closeDayRepository;

    @Transactional(readOnly = true)
    public List<CloseDayResponse> execute(LocalDate date) {
        return closeDayRepository
                .findAllByStartCloseTimeLessThanEqualAndEndCloseTimeGreaterThanEqualOrderByStartCloseTimeAscIdAsc(date, date)
                .stream()
                .map(CloseDayResponse::from)
                .toList();
    }
}
