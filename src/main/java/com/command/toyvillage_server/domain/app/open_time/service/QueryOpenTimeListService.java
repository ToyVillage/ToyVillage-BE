package com.command.toyvillage_server.domain.app.open_time.service;

import com.command.toyvillage_server.domain.app.open_time.domain.repository.OpenTimeRepository;
import com.command.toyvillage_server.domain.app.open_time.presentation.dto.response.OpenTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryOpenTimeListService {
    private final OpenTimeRepository openTimeRepository;

    @Transactional(readOnly = true)
    public List<OpenTimeResponse> execute() {
        return openTimeRepository.findAllByOrderByOpenDateAscStartOpenTimeAscIdAsc()
                .stream()
                .map(OpenTimeResponse::from)
                .toList();
    }
}
