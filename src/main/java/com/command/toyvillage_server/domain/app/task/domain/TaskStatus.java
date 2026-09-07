package com.command.toyvillage_server.domain.app.task.domain;

public enum TaskStatus {
    IN_PROGRESS,
    COMPLETED;

    public static TaskStatus of(int assigneeCount, long approvedCount) {
        return assigneeCount > 0 && assigneeCount == approvedCount ? COMPLETED : IN_PROGRESS;
    }
}
