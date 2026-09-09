package com.command.toyvillage_server.domain.app.workreport.domain;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "tbl_work_report",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_work_report_task_app_admin",
                columnNames = {"task_id", "app_admin_id"}
        )
)
public class WorkReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_report_id",nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_admin_id", nullable = false)
    private AppAdmin appAdmin;

    @Column(name = "content",nullable = false,length = 1024)
    private String content;

    @Column(name = "note")
    private String note;

    @OneToMany
    @JoinTable(
            name = "tbl_work_report_file",
            joinColumns = @JoinColumn(name = "work_report_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "file_id", unique = true, nullable = false)
    )
    private List<File> files;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(name = "rejection_reason" , length = 1000)
    private String rejectionReason;

    @Builder
    public WorkReport(Task task, AppAdmin appAdmin, String content, String note, List<File> files) {
        this.task = task;
        this.appAdmin = appAdmin;
        this.content = content;
        this.note = note;
        this.files = files == null ? new ArrayList<>() : new ArrayList<>(files);
    }

    public void update(String content, String note, List<File> files) {
        this.content = content;
        this.note = note;
        if (files != null) {
            this.files.clear();
            this.files.addAll(files);
        }
        this.status = Status.PENDING;
        this.rejectionReason = null;
    }

    public boolean isOwnedBy(Long appAdminId) {
        return appAdmin.getId().equals(appAdminId);
    }

    public boolean isApproved() {
        return status == Status.APPROVED;
    }

    public void approve(){
        this.status = Status.APPROVED;
    }

    public void reject(String rejectionReason){
        this.status = Status.REJECTED;
        this.rejectionReason = rejectionReason;
    }
}
