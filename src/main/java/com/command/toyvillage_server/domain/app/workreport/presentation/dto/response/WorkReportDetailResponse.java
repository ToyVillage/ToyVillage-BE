package com.command.toyvillage_server.domain.app.workreport.presentation.dto.response;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskPriority;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record WorkReportDetailResponse(
        Long id,
        Long taskId,
        String title,
        String name,
        String content,
        String note,
        List<FileResponse> files,
        Status status,
        String rejectionReason,
        TaskPriority priority,
        LocalDate finishDate
) {
    public static WorkReportDetailResponse from(WorkReport workReport) {
        Task task = workReport.getTask();

        return WorkReportDetailResponse.builder()
                .id(workReport.getId())
                .taskId(task.getId())
                .title(task.getTitle())
                .name(workReport.getAppAdmin().getName())
                .content(workReport.getContent())
                .note(workReport.getNote())
                .files(
                        workReport.getFiles().stream()
                                .map(FileResponse::from)
                                .toList()
                )
                .status(workReport.getStatus())
                .rejectionReason(workReport.getRejectionReason())
                .priority(task.getPriority())
                .finishDate(task.getFinishDate())
                .build();
    }
}
