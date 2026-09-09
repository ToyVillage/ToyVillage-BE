package com.command.toyvillage_server.domain.app.task.domain;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.task.exception.TaskTargetInvalidException;
import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tbl_task")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(nullable = false, length = 100, name = "task_title")
    private String title;

    @Column(name = "task_content", columnDefinition = "TEXT")
    private String content;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("id")
    @JoinTable(
            name = "tbl_task_assignee",
            joinColumns = @JoinColumn(name = "task_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "app_admin_id", nullable = false)
    )
    private List<AppAdmin> assignees = new ArrayList<>();

    @Column(nullable = false, name = "finish_date")
    private LocalDate finishDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", length = 20)
    private TaskVisibility visibility;

    @CreatedDate
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany
    @JoinTable(
            name = "tbl_task_file",
            joinColumns = @JoinColumn(name = "task_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "file_id", unique = true, nullable = false)
    )
    private List<File> files = new ArrayList<>();

    @Builder
    public Task(
            String title,
            String content,
            List<AppAdmin> assignees,
            LocalDate finishDate,
            TaskPriority priority,
            TaskVisibility visibility,
            List<File> files
    ) {
        validateAssignees(assignees);

        this.title = title;
        this.content = content;
        this.assignees = new ArrayList<>(assignees);
        this.finishDate = finishDate;
        this.priority = priority;
        this.visibility = visibilityOrDefault(visibility);
        this.files = files == null ? new ArrayList<>() : new ArrayList<>(files);
    }

    public void update(
            String title,
            String content,
            List<AppAdmin> assignees,
            LocalDate finishDate,
            TaskPriority priority,
            TaskVisibility visibility,
            List<File> files
    ) {
        validateAssignees(assignees);

        this.title = title;
        this.content = content;
        this.assignees.clear();
        this.assignees.addAll(assignees);
        this.finishDate = finishDate;
        this.priority = priority;
        this.visibility = visibilityOrDefault(visibility);
        if (files != null) {
            this.files.clear();
            this.files.addAll(files);
        }
    }

    public TaskVisibility getVisibility() {
        return visibilityOrDefault(visibility);
    }

    public boolean isAssignee(Long appAdminId) {
        return assignees.stream()
                .anyMatch(assignee -> assignee.getId().equals(appAdminId));
    }

    private static TaskVisibility visibilityOrDefault(TaskVisibility visibility) {
        return visibility == null ? TaskVisibility.ALL : visibility;
    }

    private static void validateAssignees(List<AppAdmin> assignees) {
        if (assignees == null || assignees.isEmpty()) {
            throw TaskTargetInvalidException.EXCEPTION;
        }
    }
}
