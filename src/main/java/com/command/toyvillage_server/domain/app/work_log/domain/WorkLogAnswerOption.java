package com.command.toyvillage_server.domain.app.work_log.domain;

import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogEtcAnswerRequiredException;
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
@Table(name = "tbl_work_log_answer_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLogAnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_log_answer_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_answer_id", nullable = false)
    private WorkLogAnswer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_question_option_id", nullable = false)
    private WorkLogQuestionOption option;

    @Column(name = "etc_text", length = 500)
    private String etcText;

    @Builder
    private WorkLogAnswerOption(WorkLogAnswer answer, WorkLogQuestionOption option, String etcText) {
        if (option.isEtcOption() && (etcText == null || etcText.isBlank())) {
            throw WorkLogEtcAnswerRequiredException.EXCEPTION;
        }

        this.answer = answer;
        this.option = option;

        if (option.isEtcOption()) {
            this.etcText = etcText;
        }
    }
}
