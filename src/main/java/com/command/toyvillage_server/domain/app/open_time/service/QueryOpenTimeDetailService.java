package com.command.toyvillage_server.domain.app.open_time.service;

import com.command.toyvillage_server.domain.app.open_time.domain.OpenTime;
import com.command.toyvillage_server.domain.app.open_time.domain.repository.OpenTimeRepository;
import com.command.toyvillage_server.domain.app.open_time.exception.OpenTimeNotFoundException;
import com.command.toyvillage_server.domain.app.open_time.presentation.dto.response.OpenTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryOpenTimeDetailService {
    private final OpenTimeRepository openTimeRepository;

    @Transactional(readOnly = true)
    public OpenTimeResponse execute(Long openTimeId) {
        OpenTime openTime = openTimeRepository.findById(openTimeId)
                .orElseThrow(() -> OpenTimeNotFoundException.EXCEPTION);

        return OpenTimeResponse.from(openTime);
    }
}
