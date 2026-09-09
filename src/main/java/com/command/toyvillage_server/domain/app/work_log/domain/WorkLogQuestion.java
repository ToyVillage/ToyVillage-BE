package com.command.toyvillage_server.domain.app.work_log.domain;

import com.command.toyvillage_server.domain.app.work_log.domain.enums.QuestionType;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogQuestionOptionNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tbl_work_log_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLogQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_log_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_log_template_id", nullable = false)
    private WorkLogTemplate template;

    @Column(nullable = false, length = 80)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;

    @Column(nullable = false)
    private boolean required;

    @OrderBy("number asc")
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkLogQuestionOption> options;

    @Builder
    private WorkLogQuestion(
        String question,
        QuestionType questionType,
        Integer questionOrder,
        boolean required
    ) {
        this.question = question;
        this.questionType = questionType;
        this.questionOrder = questionOrder;
        this.required = required;
        this.options = new ArrayList<>();
    }

    public void addOption(WorkLogQuestionOption option) {
        options.add(option);
        option.setQuestion(this);
    }

    public WorkLogQuestionOption findOption(Long optionId) {
        return options.stream()
            .filter(option -> option.getId().equals(optionId))
            .findFirst()
            .orElseThrow(() -> WorkLogQuestionOptionNotFoundException.EXCEPTION);
    }

    void setTemplate(WorkLogTemplate template) {
        this.template = template;
    }
}
