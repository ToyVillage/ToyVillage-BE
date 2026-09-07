package com.command.toyvillage_server.domain.app.task.presentation.dto.response;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskPriority;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
public record TaskDetailResponse(
        Long id,
        String title,
        String content,
        String assigneeName,
        int assigneeCount,
        TaskStatus status,
        TaskPriority priority,
        LocalDate finishDate,
        LocalDateTime createdAt,
        List<FileResponse> files,
        List<ReportResponse> reports,
        ProgressResponse progress
) {
    public static TaskDetailResponse from(Task task, List<WorkReport> workReports) {
        Map<Long, WorkReport> reportByAppAdminId = workReports.stream()
                .collect(Collectors.toMap(
                        workReport -> workReport.getAppAdmin().getId(),
                        Function.identity()
                ));

        List<ReportResponse> reports = task.getAssignees().stream()
                .map(assignee -> ReportResponse.of(assignee, reportByAppAdminId.get(assignee.getId())))
                .toList();

        return TaskDetailResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .content(task.getContent())
                .assigneeName(task.getAssigneeName())
                .assigneeCount(task.getAssignees().size())
                .status(TaskStatus.of(task.getAssignees().size(), countOf(reports, Status.APPROVED)))
                .priority(task.getPriority())
                .finishDate(task.getFinishDate())
                .createdAt(task.getCreatedAt())
                .files(task.getFiles().stream().map(FileResponse::from).toList())
                .reports(reports)
                .progress(ProgressResponse.from(reports))
                .build();
    }

    private static long countOf(List<ReportResponse> reports, Status status) {
        return reports.stream().filter(report -> report.status() == status).count();
    }

    @Builder
    private record ReportResponse(
            Long workReportId,
            Long appAdminId,
            String name,
            Status status
    ) {
        private static ReportResponse of(AppAdmin assignee, WorkReport workReport) {
            return ReportResponse.builder()
                    .workReportId(workReport == null ? null : workReport.getId())
                    .appAdminId(assignee.getId())
                    .name(assignee.getName())
                    .status(workReport == null ? Status.MISSING : workReport.getStatus())
                    .build();
        }
    }

    @Builder
    private record ProgressResponse(
            int total,
            long approved,
            long rejected,
            long pending,
            long missing
    ) {
        private static ProgressResponse from(List<ReportResponse> reports) {
            return ProgressResponse.builder()
                    .total(reports.size())
                    .approved(countOf(reports, Status.APPROVED))
                    .rejected(countOf(reports, Status.REJECTED))
                    .pending(countOf(reports, Status.PENDING))
                    .missing(countOf(reports, Status.MISSING))
                    .build();
        }
    }
}
