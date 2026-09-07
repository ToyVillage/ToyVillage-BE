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

@Builder
public record TaskListResponse(
        List<TaskResponse> tasks,
        int totalPageSize
) {
    public static TaskListResponse from(Page<Task> tasks, Map<Long, List<WorkReport>> workReportsByTaskId) {
        return TaskListResponse.builder()
                .tasks(tasks.map(task -> TaskResponse.of(
                        task,
                        workReportsByTaskId.getOrDefault(task.getId(), List.of())
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
        private static TaskResponse of(Task task, List<WorkReport> workReports) {
            long approved = workReports.stream()
                    .filter(workReport -> workReport.getStatus() == Status.APPROVED)
                    .count();

            return TaskResponse.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .assigneeName(task.getAssigneeName())
                    .assigneeCount(task.getAssignees().size())
                    .status(TaskStatus.of(task.getAssignees().size(), approved))
                    .priority(task.getPriority())
                    .finishDate(task.getFinishDate())
                    .build();
        }
    }
}
