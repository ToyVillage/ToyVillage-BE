package com.command.toyvillage_server.domain.app.close_day.service;

import com.command.toyvillage_server.domain.app.close_day.domain.CloseDay;
import com.command.toyvillage_server.domain.app.close_day.domain.repository.CloseDayRepository;
import com.command.toyvillage_server.domain.app.close_day.exception.CloseDayNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CloseDayDeleteService {
    private final CloseDayRepository closeDayRepository;

    @Transactional
    public void execute(Long closeDayId) {
        CloseDay closeDay = closeDayRepository.findById(closeDayId)
                .orElseThrow(() -> CloseDayNotFoundException.EXCEPTION);

        closeDayRepository.delete(closeDay);
    }
}
