package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
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
public class QueryTaskListService {
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;

    @Transactional(readOnly = true)
    public TaskListResponse execute(TaskStatus status, Pageable pageable) {
        LocalDate today = LocalDate.now();
        Page<Task> tasks = findTasks(status, today, pageable);

        List<Long> taskIds = tasks.map(Task::getId).toList();
        Map<Long, List<WorkReport>> workReportsByTaskId = taskIds.isEmpty()
                ? Map.of()
                : workReportRepository.findAllByTask_IdIn(taskIds).stream()
                        .collect(Collectors.groupingBy(workReport -> workReport.getTask().getId()));

        return TaskListResponse.from(tasks, workReportsByTaskId, today);
    }

    private Page<Task> findTasks(TaskStatus status, LocalDate today, Pageable pageable) {
        if (status == null) {
            return taskRepository.findAll(pageable);
        }

        return switch (status) {
            case COMPLETED -> taskRepository.findAllCompleted(pageable);
            case IN_PROGRESS -> taskRepository.findAllInProgress(today, pageable);
            case EXPIRED -> taskRepository.findAllExpired(today, pageable);
        };
    }
}
