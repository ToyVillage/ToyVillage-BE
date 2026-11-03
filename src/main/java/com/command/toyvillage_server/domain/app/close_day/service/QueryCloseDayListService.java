package com.command.toyvillage_server.domain.app.close_day.service;

import com.command.toyvillage_server.domain.app.close_day.domain.repository.CloseDayRepository;
import com.command.toyvillage_server.domain.app.close_day.presentation.dto.response.CloseDayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryCloseDayListService {
    private final CloseDayRepository closeDayRepository;

    @Transactional(readOnly = true)
    public List<CloseDayResponse> execute() {
        return closeDayRepository.findAllByOrderByStartCloseTimeAscIdAsc()
                .stream()
                .map(CloseDayResponse::from)
                .toList();
    }
}
