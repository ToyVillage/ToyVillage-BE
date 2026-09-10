package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.task.presentation.dto.response.TaskListResponse;
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
    public TaskListResponse execute(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAllAssignedTo(userFacade.getCurrentUserId(), pageable);

        List<Long> taskIds = tasks.map(Task::getId).toList();
        Map<Long, List<WorkReport>> workReportsByTaskId = taskIds.isEmpty()
                ? Map.of()
                : workReportRepository.findAllByTask_IdIn(taskIds).stream()
                        .collect(Collectors.groupingBy(workReport -> workReport.getTask().getId()));

        return TaskListResponse.from(tasks, workReportsByTaskId, LocalDate.now());
    }
}
