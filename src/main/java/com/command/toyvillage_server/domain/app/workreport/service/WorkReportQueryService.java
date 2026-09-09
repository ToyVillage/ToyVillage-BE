package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkNotFoundException;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.response.WorkReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkReportQueryService {
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public WorkReportResponse execute(Long taskId) {
        Long currentUserId = userFacade.getCurrentUserId();

        WorkReport workReport = workReportRepository.findByTask_IdAndAppAdmin_Id(taskId,currentUserId)
                .orElseThrow(() -> WorkNotFoundException.EXCEPTION);
        return WorkReportResponse.from(workReport);
    }
}
