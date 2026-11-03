package com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response;

public record DashBoardOverallOperationsResponse(
        Long TOTAL,
        Long IN_PROGRESS,
        Long COMPLETED,
        Long EXPIRED
) {
    public static DashBoardOverallOperationsResponse of(
            long inProgress,
            long completed,
            long expired
    ) {
        return new DashBoardOverallOperationsResponse(
                inProgress + completed + expired,
                inProgress,
                completed,
                expired
        );
    }
}
