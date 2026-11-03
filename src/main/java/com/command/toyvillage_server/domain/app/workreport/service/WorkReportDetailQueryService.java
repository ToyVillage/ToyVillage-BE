package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkNotFoundException;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.response.WorkReportDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkReportDetailQueryService {
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public WorkReportDetailResponse execute(Long workReportId) {
        WorkReport workReport = workReportRepository.findById(workReportId)
                .orElseThrow(() -> WorkNotFoundException.EXCEPTION);

        if (!userFacade.isCurrentUserAppAdmin()
                && !workReport.getTask().isCreatedBy(userFacade.getCurrentUserId())) {
            throw WorkNotFoundException.EXCEPTION;
        }

        return WorkReportDetailResponse.from(workReport);
    }
}
