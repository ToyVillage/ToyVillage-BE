package com.command.toyvillage_server.domain.app.workreport.presentation.dto.response;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskPriority;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Builder
public record WorkReportListResponse(
        List<ReportResponse> reports,
        int totalPageSize,
        long pendingCount,
        long approvedCount,
        long rejectedCount
) {
    public static WorkReportListResponse of(
            Page<WorkReport> workReports,
            long pendingCount,
            long approvedCount,
            long rejectedCount
    ) {
        return WorkReportListResponse.builder()
                .reports(workReports.map(ReportResponse::from).toList())
                .totalPageSize(workReports.getTotalPages())
                .pendingCount(pendingCount)
                .approvedCount(approvedCount)
                .rejectedCount(rejectedCount)
                .build();
    }

    @Builder
    private record ReportResponse(
            Long id,
            Long taskId,
            String name,
            String title,
            Status status,
            TaskPriority priority,
            LocalDate finishDate
    ) {
        private static ReportResponse from(WorkReport workReport) {
            Task task = workReport.getTask();

            return ReportResponse.builder()
                    .id(workReport.getId())
                    .taskId(task.getId())
                    .name(workReport.getAppAdmin().getName())
                    .title(task.getTitle())
                    .status(workReport.getStatus())
                    .priority(task.getPriority())
                    .finishDate(task.getFinishDate())
                    .build();
        }
    }
}
