package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.task.exception.TaskNotFoundException;
import com.command.toyvillage_server.domain.app.task.presentation.dto.request.TaskRequest;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.service.FileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateTaskService {
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;
    private final TaskAssigneeService taskAssigneeService;
    private final FileFacade fileFacade;

    @Transactional
    public void execute(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> TaskNotFoundException.EXCEPTION);

        List<AppAdmin> assignees = taskAssigneeService.execute(request.assigneeIds());
        List<File> files = request.files() == null ? null : fileFacade.findAllByKeys(request.files());

        task.update(
                request.title(),
                request.content(),
                assignees,
                request.finishDate(),
                request.priority(),
                request.visibility(),
                files
        );

        deleteReportsOfRemovedAssignees(id, assignees);
    }

    private void deleteReportsOfRemovedAssignees(Long taskId, List<AppAdmin> assignees) {
        Set<Long> assigneeIds = assignees.stream()
                .map(AppAdmin::getId)
                .collect(Collectors.toSet());

        List<WorkReport> removedAssigneeReports = workReportRepository.findAllByTask_Id(taskId).stream()
                .filter(workReport -> !assigneeIds.contains(workReport.getAppAdmin().getId()))
                .toList();

        workReportRepository.deleteAll(removedAssigneeReports);
    }
}
