package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.task.presentation.dto.response.MyTaskListResponse;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryMyTaskListService {
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public MyTaskListResponse execute(TaskStatus status, Pageable pageable) {
        Long appAdminId = userFacade.getCurrentUserId();
        LocalDate today = LocalDate.now();
        Page<Task> tasks = findTasks(appAdminId, status, today, pageable);

        List<Long> taskIds = tasks.map(Task::getId).toList();
        Map<Long, List<WorkReport>> workReportsByTaskId = taskIds.isEmpty()
                ? Map.of()
                : workReportRepository.findAllByTask_IdIn(taskIds).stream()
                        .collect(Collectors.groupingBy(workReport -> workReport.getTask().getId()));

        return MyTaskListResponse.from(tasks, workReportsByTaskId, appAdminId, today);
    }

    private Page<Task> findTasks(Long appAdminId, TaskStatus status, LocalDate today, Pageable pageable) {
        if (status == null) {
            return taskRepository.findAllAssignedTo(appAdminId, pageable);
        }

        return switch (status) {
            case COMPLETED -> taskRepository.findAllAssignedToCompleted(appAdminId, pageable);
            case IN_PROGRESS -> taskRepository.findAllAssignedToInProgress(appAdminId, today, pageable);
            case EXPIRED -> taskRepository.findAllAssignedToExpired(appAdminId, today, pageable);
        };
    }
}
