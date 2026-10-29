package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkAlreadyApprovedException;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkApproveService {
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long id) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> WorkNotFoundException.EXCEPTION);

        if (!userFacade.isCurrentUserAppAdmin()
                && !workReport.getTask().isCreatedBy(userFacade.getCurrentUserId())) {
            throw WorkNotFoundException.EXCEPTION;
        }

        if (workReport.getStatus() == Status.APPROVED) {
            throw WorkAlreadyApprovedException.EXCEPTION;
        }

        workReport.approve();
    }
}
