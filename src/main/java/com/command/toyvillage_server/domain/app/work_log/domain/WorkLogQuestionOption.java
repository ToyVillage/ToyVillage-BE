package com.command.toyvillage_server.domain.app.work_log.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tbl_work_log_question_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLogQuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_log_question_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_question_id", nullable = false)
    private WorkLogQuestion question;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false, length = 30)
    private String content;

    @Column(name = "etc_option", nullable = false)
    private boolean etcOption;

    @Builder
    private WorkLogQuestionOption(Integer number, String content, boolean etcOption) {
        this.number = number;
        this.content = content;
        this.etcOption = etcOption;
    }

    void setQuestion(WorkLogQuestion question) {
        this.question = question;
    }
}
