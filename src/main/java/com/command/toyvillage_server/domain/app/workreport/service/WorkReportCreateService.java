package com.command.toyvillage_server.domain.app.workreport.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkNotFoundException;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkReportAlreadyExistsException;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.request.WorkReportRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.service.FileFacade;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkReportCreateService {
    private static final String DUPLICATE_CONSTRAINT = "uk_work_report_task_app_admin";

    private final WorkReportRepository workReportRepository;
    private final TaskRepository taskRepository;
    private final AppAdminRepository appAdminRepository;
    private final FileFacade fileFacade;
    private final UserFacade userFacade;

    @Transactional
    public Long execute(Long taskId, WorkReportRequest workReportRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> WorkNotFoundException.EXCEPTION);

        Long currentUserId = userFacade.getCurrentUserId();
        if (!task.isAssignee(currentUserId)) {
            throw WorkNotFoundException.EXCEPTION;
        }

        if (workReportRepository.existsByTask_IdAndAppAdmin_Id(taskId, currentUserId)) {
            throw WorkReportAlreadyExistsException.EXCEPTION;
        }

        AppAdmin appAdmin = appAdminRepository.findById(currentUserId)
                .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        List<File> files = fileFacade.findAllByKeys(workReportRequest.fileKey());

        WorkReport workReport = WorkReport.builder()
                .task(task)
                .appAdmin(appAdmin)
                .content(workReportRequest.content())
                .note(workReportRequest.note())
                .files(files)
                .build();

        try {
            return workReportRepository.saveAndFlush(workReport).getId();
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateReport(e)) {
                throw WorkReportAlreadyExistsException.EXCEPTION;
            }
            throw e;
        }
    }

    private boolean isDuplicateReport(DataIntegrityViolationException e) {
        if (!(e.getCause() instanceof ConstraintViolationException constraintViolation)) {
            return false;
        }

        String constraintName = constraintViolation.getConstraintName();
        return constraintName != null && constraintName.toLowerCase().contains(DUPLICATE_CONSTRAINT);
    }
}
