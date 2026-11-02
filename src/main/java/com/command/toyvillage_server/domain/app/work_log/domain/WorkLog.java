package com.command.toyvillage_server.domain.app.work_log.domain;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tbl_work_log")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_template_id", nullable = false)
    private WorkLogTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_admin_id", nullable = false)
    private AppAdmin appAdmin;

    @Column(name = "title", length = 100)
    private String title;

    @CreatedDate
    @Column(name = "write_at", nullable = false, updatable = false)
    private LocalDateTime writeAt;

    @Column(name = "write_time_known", nullable = false)
    private boolean writeTimeKnown = true;

    @OneToMany(mappedBy = "workLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkLogAnswer> answers = new ArrayList<>();

    @Builder
    private WorkLog(WorkLogTemplate template, AppAdmin appAdmin, String title) {
        this.template = template;
        this.appAdmin = appAdmin;
        this.title = title;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void addAnswer(WorkLogAnswer answer) {
        answers.add(answer);
        answer.setWorkLog(this);
    }

    public void replaceAnswers(List<WorkLogAnswer> newAnswers) {
        answers.clear();
        newAnswers.forEach(this::addAnswer);
    }

    public boolean isWrittenBy(Long appAdminId) {
        return appAdmin.getId().equals(appAdminId);
    }
}
