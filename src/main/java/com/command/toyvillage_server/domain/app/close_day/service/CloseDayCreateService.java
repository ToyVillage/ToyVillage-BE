package com.command.toyvillage_server.domain.app.close_day.service;

import com.command.toyvillage_server.domain.app.close_day.domain.CloseDay;
import com.command.toyvillage_server.domain.app.close_day.domain.repository.CloseDayRepository;
import com.command.toyvillage_server.domain.app.close_day.presentation.dto.request.CloseDayRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CloseDayCreateService {
    private final CloseDayRepository closeDayRepository;

    @Transactional
    public void execute(CloseDayRequest request) {
        CloseDayPeriodValidator.validate(request.startCloseTime(), request.endCloseTime());

        CloseDay closeDay = CloseDay.create(
                request.title(),
                request.startCloseTime(),
                request.endCloseTime()
        );

        closeDayRepository.save(closeDay);
    }
}
