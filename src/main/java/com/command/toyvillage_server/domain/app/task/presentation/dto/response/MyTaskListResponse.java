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
public record MyTaskListResponse(
        List<MyTaskResponse> tasks,
        int totalPageSize
) {
    public static MyTaskListResponse from(
            Page<Task> tasks,
            Map<Long, List<WorkReport>> workReportsByTaskId,
            Long appAdminId,
            LocalDate today
    ) {
        return MyTaskListResponse.builder()
                .tasks(tasks.map(task -> MyTaskResponse.of(
                        task,
                        workReportsByTaskId.getOrDefault(task.getId(), List.of()),
                        appAdminId,
                        today
                )).toList())
                .totalPageSize(tasks.getTotalPages())
                .build();
    }

    @Builder
    private record MyTaskResponse(
            Long id,
            String title,
            List<AssigneeResponse> assignees,
            int assigneeCount,
            TaskStatus status,
            Status myReportStatus,
            TaskPriority priority,
            LocalDate finishDate
    ) {
        private static MyTaskResponse of(
                Task task,
                List<WorkReport> workReports,
                Long appAdminId,
                LocalDate today
        ) {
            Set<Long> assigneeIds = task.getAssignees().stream()
                    .map(assignee -> assignee.getId())
                    .collect(Collectors.toSet());

            long approved = workReports.stream()
                    .filter(workReport -> workReport.getStatus() == Status.APPROVED)
                    .filter(workReport -> assigneeIds.contains(workReport.getAppAdmin().getId()))
                    .count();

            return MyTaskResponse.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .assignees(AssigneeResponse.listOf(task.getAssignees()))
                    .assigneeCount(task.getAssignees().size())
                    .status(TaskStatus.of(task.getAssignees().size(), approved, task.getFinishDate(), today))
                    .myReportStatus(myReportStatus(workReports, appAdminId))
                    .priority(task.getPriority())
                    .finishDate(task.getFinishDate())
                    .build();
        }

        private static Status myReportStatus(List<WorkReport> workReports, Long appAdminId) {
            return workReports.stream()
                    .filter(workReport -> workReport.getAppAdmin().getId().equals(appAdminId))
                    .map(WorkReport::getStatus)
                    .findFirst()
                    .orElse(Status.MISSING);
        }
    }
}
