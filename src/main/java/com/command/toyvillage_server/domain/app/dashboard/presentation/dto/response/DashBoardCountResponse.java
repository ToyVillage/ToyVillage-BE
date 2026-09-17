package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

public record DashBoardCountResponse(
        Integer feedLogCount,
        Integer animalCount,
        Integer workReportCount,
        Integer workLogCount
) {
    public static DashBoardCountResponse of(
            long feedLogCount,
            long animalCount,
            long workReportCount,
            long workLogCount
    ) {
        return new DashBoardCountResponse(
                Math.toIntExact(feedLogCount),
                Math.toIntExact(animalCount),
                Math.toIntExact(workReportCount),
                Math.toIntExact(workLogCount)
        );
    }
}
