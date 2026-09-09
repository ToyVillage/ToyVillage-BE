package com.command.toyvillage_server.domain.app.work_log.service.work_log;

import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkLogAdminQueryListService {
    private final WorkLogRepository workLogRepository;

    @Transactional(readOnly = true)
    public Page<WorkLogListResponse> execute(LocalDate date, Pageable p) {
        return workLogRepository.findByWriteAt(date, p)
            .map(WorkLogListResponse::from);
    }
}
