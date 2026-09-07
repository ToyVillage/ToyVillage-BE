package com.command.toyvillage_server.domain.app.work_log.presentation.dto.response;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswer;
import com.command.toyvillage_server.domain.app.work_log.domain.enums.QuestionType;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkLogAnswerResponse(
    Long questionId,
    String question,
    QuestionType questionType,
    String answerText,
    List<WorkLogAnswerOptionResponse> options,
    FileResponse file
) {
    public static WorkLogAnswerResponse from(WorkLogAnswer answer) {
        FileResponse file = null;

        if (answer.getFile() != null) {
            file = FileResponse.from(answer.getFile());
        }

        return WorkLogAnswerResponse.builder()
            .questionId(answer.getQuestion().getId())
            .question(answer.getQuestion().getQuestion())
            .questionType(answer.getQuestion().getQuestionType())
            .answerText(answer.getAnswerText())
            .options(answer.getSelectedOptions().stream()
                .map(WorkLogAnswerOptionResponse::from)
                .toList())
            .file(file)
            .build();
    }
}
