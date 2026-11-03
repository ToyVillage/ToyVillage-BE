package com.command.toyvillage_server.domain.web.news.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class NewsRequest {

    @NotBlank(message = "뉴스 제목을 비워둘 순 없습니다.")
    @JsonProperty("news_title")
    private String title;

    @NotBlank(message = "뉴스 내용을 비워둘 순 없습니다.")
    @JsonProperty("news_description")
    private String description;

    @JsonProperty("file_keys")
    
    private List<String> fileKeys;
}
