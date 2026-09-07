package com.command.toyvillage_server.domain.app.task.domain;

import java.time.LocalDate;

public enum TaskStatus {
    IN_PROGRESS,
    COMPLETED,
    EXPIRED;

    public static TaskStatus of(int assigneeCount, long approvedCount, LocalDate finishDate, LocalDate today) {
        if (assigneeCount > 0 && assigneeCount == approvedCount) {
            return COMPLETED;
        }
        return finishDate.isBefore(today) ? EXPIRED : IN_PROGRESS;
    }
}
