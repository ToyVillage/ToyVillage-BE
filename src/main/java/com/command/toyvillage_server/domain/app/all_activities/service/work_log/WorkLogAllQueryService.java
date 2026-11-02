package com.command.toyvillage_server.domain.app.all_activities.service.work_log;

import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.work_log.WorkLogAllResponse;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogAllQueryService {
    private final WorkLogRepository workLogRepository;

    @Transactional(readOnly = true)
    public List<WorkLogAllResponse> execute(LocalDate date) {
        return workLogRepository.findAllByWriteAtOrderByIdAsc(date)
                .stream()
                .map(WorkLogAllResponse::from)
                .toList();
    }
}
