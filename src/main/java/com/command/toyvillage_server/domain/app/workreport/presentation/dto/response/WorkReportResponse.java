package com.command.toyvillage_server.domain.app.workreport.presentation.dto.response;

import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkReportResponse(
        Long id,
        Long taskId,
        String title,
        String content,
        String note,
        List<FileResponse> files,
        Status status,
        String rejectionReason
) {
    public static WorkReportResponse from(WorkReport workReport) {
        return WorkReportResponse.builder()
                .id(workReport.getId())
                .taskId(workReport.getTask().getId())
                .title(workReport.getTask().getTitle())
                .content(workReport.getContent())
                .note(workReport.getNote())
                .files(
                        workReport.getFiles().stream()
                                .map(FileResponse::from)
                                .toList()
                )
                .status(workReport.getStatus())
                .rejectionReason(workReport.getRejectionReason())
                .build();
    }
}
