package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

public record DashBoardCountResponse(
        Long feedLogCount,
        Long animalCount,
        Long workReportCount,
        Long workLogCount
) {
    public static DashBoardCountResponse of(
            long feedLogCount,
            long animalCount,
            long workReportCount,
            long workLogCount
    ) {
        return new DashBoardCountResponse(
                feedLogCount,
                animalCount,
                workReportCount,
                workLogCount
        );
    }
}
