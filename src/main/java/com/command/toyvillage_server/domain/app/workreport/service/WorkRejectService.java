package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkAlreadyRejectedException;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkNotFoundException;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.request.WorkRejectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkRejectService {
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long id, WorkRejectRequest workRejectRequest) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> WorkNotFoundException.EXCEPTION);

        if (!userFacade.isCurrentUserAppAdmin()
                && !workReport.getTask().isCreatedBy(userFacade.getCurrentUserId())) {
            throw WorkNotFoundException.EXCEPTION;
        }

        if (workReport.getStatus() == Status.REJECTED) {
            throw WorkAlreadyRejectedException.EXCEPTION;
        }

        workReport.reject(workRejectRequest.rejectionReason());
    }
}
