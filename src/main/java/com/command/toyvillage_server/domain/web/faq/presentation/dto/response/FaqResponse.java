package com.command.toyvillage_server.domain.web.faq.presentation.dto.response;

import com.command.toyvillage_server.domain.web.faq.domain.Faq;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FaqResponse(
    @JsonProperty("question_id")
    Long questionId,
    
    @JsonProperty("question_content")
    String questionContent,

    @JsonProperty("question_answer")
    String questionAnswer
) {
    public static FaqResponse from(Faq faq) {
        return FaqResponse.builder()
            .questionId(faq.getId())
            .questionContent(faq.getContent())
            .questionAnswer(faq.getAnswer())
            .build();
    }
}
