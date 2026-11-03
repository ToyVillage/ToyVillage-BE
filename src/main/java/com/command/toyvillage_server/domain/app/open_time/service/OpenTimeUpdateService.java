package com.command.toyvillage_server.domain.app.open_time.service;

import com.command.toyvillage_server.domain.app.open_time.domain.OpenTime;
import com.command.toyvillage_server.domain.app.open_time.domain.repository.OpenTimeRepository;
import com.command.toyvillage_server.domain.app.open_time.exception.OpenTimeNotFoundException;
import com.command.toyvillage_server.domain.app.open_time.presentation.dto.request.OpenTimeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OpenTimeUpdateService {
    private final OpenTimeRepository openTimeRepository;

    @Transactional
    public void execute(Long openTimeId, OpenTimeRequest request) {
        OpenTimePeriodValidator.validate(request.startOpenTime(), request.endOpenTime());

        OpenTime openTime = openTimeRepository.findById(openTimeId)
                .orElseThrow(() -> OpenTimeNotFoundException.EXCEPTION);

        openTime.update(
                request.openDate(),
                request.startOpenTime(),
                request.endOpenTime()
        );
    }
}
