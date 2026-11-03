package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.response.WorkReportListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkReportAllQueryService {
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public WorkReportListResponse execute(Status status, Pageable pageable) {
        if (!userFacade.isCurrentUserAppAdmin()) {
            return queryCreatedBy(userFacade.getCurrentUserId(), status, pageable);
        }

        Page<WorkReport> workReports = status == null
                ? workReportRepository.findAll(pageable)
                : workReportRepository.findAllByStatus(status, pageable);

        return WorkReportListResponse.of(
                workReports,
                workReportRepository.countByStatus(Status.PENDING),
                workReportRepository.countByStatus(Status.APPROVED),
                workReportRepository.countByStatus(Status.REJECTED),
                workReportRepository.countByStatus(Status.RESUBMIT)
        );
    }

    private WorkReportListResponse queryCreatedBy(Long creatorId, Status status, Pageable pageable) {
        Page<WorkReport> workReports = status == null
                ? workReportRepository.findAllByTask_Creator_Id(creatorId, pageable)
                : workReportRepository.findAllByStatusAndTask_Creator_Id(status, creatorId, pageable);

        return WorkReportListResponse.of(
                workReports,
                workReportRepository.countByStatusAndTask_Creator_Id(Status.PENDING, creatorId),
                workReportRepository.countByStatusAndTask_Creator_Id(Status.APPROVED, creatorId),
                workReportRepository.countByStatusAndTask_Creator_Id(Status.REJECTED, creatorId),
                workReportRepository.countByStatusAndTask_Creator_Id(Status.RESUBMIT, creatorId)
        );
    }
}
