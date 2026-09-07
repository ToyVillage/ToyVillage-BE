package com.command.toyvillage_server.domain.app.task.presentation.dto.response;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskPriority;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record TaskListResponse(
        List<TaskResponse> tasks,
        int totalPageSize
) {
    public static TaskListResponse from(
            Page<Task> tasks,
            Map<Long, List<WorkReport>> workReportsByTaskId,
            LocalDate today
    ) {
        return TaskListResponse.builder()
                .tasks(tasks.map(task -> TaskResponse.of(
                        task,
                        workReportsByTaskId.getOrDefault(task.getId(), List.of()),
                        today
                )).toList())
                .totalPageSize(tasks.getTotalPages())
                .build();
    }

    @Builder
    private record TaskResponse(
            Long id,
            String title,
            String assigneeName,
            int assigneeCount,
            TaskStatus status,
            TaskPriority priority,
            LocalDate finishDate
    ) {
        private static TaskResponse of(Task task, List<WorkReport> workReports, LocalDate today) {
            Set<Long> assigneeIds = task.getAssignees().stream()
                    .map(assignee -> assignee.getId())
                    .collect(Collectors.toSet());

            long approved = workReports.stream()
                    .filter(workReport -> workReport.getStatus() == Status.APPROVED)
                    .filter(workReport -> assigneeIds.contains(workReport.getAppAdmin().getId()))
                    .count();

            return TaskResponse.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .assigneeName(task.getAssigneeName())
                    .assigneeCount(task.getAssignees().size())
                    .status(TaskStatus.of(task.getAssignees().size(), approved, task.getFinishDate(), today))
                    .priority(task.getPriority())
                    .finishDate(task.getFinishDate())
                    .build();
        }
    }
}
