package com.command.toyvillage_server.domain.app.work_log.domain;

import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogSingleOptionOnlyException;
import com.command.toyvillage_server.domain.web.file.domain.File;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tbl_work_log_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLogAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_log_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_id", nullable = false)
    private WorkLog workLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_section_id", nullable = false)
    private WorkLogSection section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_question_id", nullable = false)
    private WorkLogQuestion question;

    @Column(name = "answer_text", length = 500)
    private String answerText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkLogAnswerOption> selectedOptions = new ArrayList<>();

    @Builder
    private WorkLogAnswer(
        WorkLogSection section,
        WorkLogQuestion question,
        String answerText,
        File file
    ) {
        this.section = section;
        this.question = question;
        this.answerText = answerText;
        this.file = file;
    }

    public void selectOption(Long optionId, String etcText) {
        if (!question.getQuestionType().isMultipleSelectable() && !selectedOptions.isEmpty()) {
            throw WorkLogSingleOptionOnlyException.EXCEPTION;
        }

        selectedOptions.add(WorkLogAnswerOption.builder()
            .answer(this)
            .option(question.findOption(optionId))
            .etcText(etcText)
            .build());
    }

    public boolean isFilled() {
        return file != null
            || !selectedOptions.isEmpty()
            || (answerText != null && !answerText.isBlank());
    }

    void setWorkLog(WorkLog workLog) {
        this.workLog = workLog;
    }
}
