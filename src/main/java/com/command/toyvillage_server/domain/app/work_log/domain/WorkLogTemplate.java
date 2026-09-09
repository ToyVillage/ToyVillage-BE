package com.command.toyvillage_server.domain.app.work_log.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogAnswerRequiredException;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogQuestionNotFoundException;
import com.command.toyvillage_server.domain.app.work_log.exception.WorkLogSectionNotFoundException;
import com.command.toyvillage_server.domain.web.file.domain.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "tbl_work_log_template")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLogTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_log_template_id")
    private Long id;

    @Column(name = "template_title", nullable = false, unique = true, length = 50)
    private String templateTitle;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @OrderBy("questionOrder asc")
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkLogQuestion> questions = new ArrayList<>();

    @OrderBy("sectionOrder asc")
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkLogSection> sections = new ArrayList<>();

    @Column(name = "delete_yn", nullable = false)
    private boolean deleteYn;

    @Builder
    private WorkLogTemplate(String templateTitle) {
        this.templateTitle = templateTitle;
        this.deleteYn = false;
    }

    public void addQuestion(WorkLogQuestion question) {
        questions.add(question);
        question.setTemplate(this);
    }

    public void addSection(WorkLogSection section) {
        sections.add(section);
        section.setTemplate(this);
    }

    public WorkLogAnswer createAnswer(Long sectionId, Long questionId, String answerText, File file) {
        return WorkLogAnswer.builder()
            .section(findSection(sectionId))
            .question(findQuestion(questionId))
            .answerText(answerText)
            .file(file)
            .build();
    }

    public void validateRequiredAnswered(List<WorkLogAnswer> answers) {
        Set<String> answered = answers.stream()
            .filter(WorkLogAnswer::isFilled)
            .map(answer -> key(answer.getSection().getId(), answer.getQuestion().getId()))
            .collect(Collectors.toCollection(HashSet::new));

        questions.stream()
            .filter(WorkLogQuestion::isRequired)
            .forEach(question -> sections.forEach(section -> {
                if (!answered.contains(key(section.getId(), question.getId()))) {
                    throw WorkLogAnswerRequiredException.EXCEPTION;
                }
            }));
    }

    public void changeDeleteYn() {
        this.deleteYn = true;
    }

    private WorkLogSection findSection(Long sectionId) {
        return sections.stream()
            .filter(section -> section.getId().equals(sectionId))
            .findFirst()
            .orElseThrow(() -> WorkLogSectionNotFoundException.EXCEPTION);
    }

    private WorkLogQuestion findQuestion(Long questionId) {
        return questions.stream()
            .filter(question -> question.getId().equals(questionId))
            .findFirst()
            .orElseThrow(() -> WorkLogQuestionNotFoundException.EXCEPTION);
    }

    private String key(Long sectionId, Long questionId) {
        return sectionId + ":" + questionId;
    }
}
