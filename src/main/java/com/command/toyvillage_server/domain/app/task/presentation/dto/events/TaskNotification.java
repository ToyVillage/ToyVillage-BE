package com.command.toyvillage_server.domain.app.task.presentation.dto.events;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;

import java.util.List;

public record TaskNotification(Long taskId, Long workReportId, String title, Type type, List<Long> recipientIds) {
    public enum Type {
        TASK_ASSIGNED,
        TASK_COMPLETED,
        TASK_APPROVED,
        TASK_REJECTED
    }

    public static TaskNotification assigned(Task task, List<Long> recipientIds) {
        return new TaskNotification(task.getId(), null, task.getTitle(), Type.TASK_ASSIGNED, recipientIds);
    }

    public static TaskNotification completed(WorkReport report) {
        Task task = report.getTask();
        List<Long> recipientIds = task.getCreator() == null ? List.of() : List.of(task.getCreator().getId());
        return new TaskNotification(task.getId(), report.getId(), task.getTitle(), Type.TASK_COMPLETED, recipientIds);
    }

    public static TaskNotification approved(WorkReport report) {
        return new TaskNotification(report.getTask().getId(), report.getId(), report.getTask().getTitle(),
                Type.TASK_APPROVED, List.of(report.getAppAdmin().getId()));
    }

    public static TaskNotification rejected(WorkReport report) {
        return new TaskNotification(report.getTask().getId(), report.getId(), report.getTask().getTitle(),
                Type.TASK_REJECTED, List.of(report.getAppAdmin().getId()));
    }
}
