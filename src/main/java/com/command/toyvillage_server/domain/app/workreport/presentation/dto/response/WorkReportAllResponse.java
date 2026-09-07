package com.command.toyvillage_server.domain.app.workreport.presentation.dto.response;

import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import lombok.Builder;

@Builder
public record WorkReportAllResponse(
        Long id,
        Long taskId,
        String name,
        String title,
        Status status
) {
    public static WorkReportAllResponse from(WorkReport workReport) {
        return WorkReportAllResponse.builder()
                .id(workReport.getId())
                .taskId(workReport.getTask().getId())
                .name(workReport.getAppAdmin().getName())
                .title(workReport.getTask().getTitle())
                .status(workReport.getStatus())
                .build();
    }
}
