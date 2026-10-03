package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogQuestion;
import com.command.toyvillage_server.domain.app.work_log.domain.enums.QuestionType;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkLogQuestionResponse(
    Long questionId,
    String question,
    QuestionType questionType,
    boolean required,
    List<WorkLogQuestionOptionResponse> options
) {
    public static WorkLogQuestionResponse from(WorkLogQuestion question) {
        return WorkLogQuestionResponse.builder()
            .questionId(question.getId())
            .question(question.getQuestion())
            .questionType(question.getQuestionType())
            .required(question.isRequired())
            .options(question.getOptions().stream()
                .map(WorkLogQuestionOptionResponse::from)
                .toList())
            .build();
    }
}
