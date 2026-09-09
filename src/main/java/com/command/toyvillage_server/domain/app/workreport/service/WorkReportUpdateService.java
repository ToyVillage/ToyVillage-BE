package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkAlreadyApprovedException;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkNotFoundException;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.request.WorkReportRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.service.FileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkReportUpdateService {
    private final WorkReportRepository workReportRepository;
    private final FileFacade fileFacade;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long workReportId, WorkReportRequest workReportRequest) {
        WorkReport workReport = workReportRepository.findById(workReportId)
                .orElseThrow(() -> WorkNotFoundException.EXCEPTION);

        if (!workReport.isOwnedBy(userFacade.getCurrentUserId())) {
            throw WorkNotFoundException.EXCEPTION;
        }

        if (workReport.isApproved()) {
            throw WorkAlreadyApprovedException.EXCEPTION;
        }

        List<File> files = workReportRequest.fileKey() == null
                ? null
                : fileFacade.findAllByKeys(workReportRequest.fileKey());

        workReport.update(workReportRequest.content(), workReportRequest.note(), files);
    }
}
