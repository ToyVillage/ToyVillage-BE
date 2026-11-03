package com.command.toyvillage_server.domain.app.open_time.service;

import com.command.toyvillage_server.domain.app.open_time.domain.repository.OpenTimeRepository;
import com.command.toyvillage_server.domain.app.open_time.presentation.dto.response.OpenTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryOpenTimeByDateService {
    private final OpenTimeRepository openTimeRepository;

    @Transactional(readOnly = true)
    public List<OpenTimeResponse> execute(LocalDate date) {
        List<OpenTimeResponse> openTimes = openTimeRepository.findAllByOpenDateOrderByStartOpenTimeAscIdAsc(date)
                .stream()
                .map(OpenTimeResponse::from)
                .toList();

        if (openTimes.isEmpty()) {
            return List.of(OpenTimeResponse.defaultFor(date));
        }

        return openTimes;
    }
}
