package com.command.toyvillage_server.domain.web.news.presentation.dto.response;

import com.command.toyvillage_server.domain.web.news.domain.News;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime postdate;
    @JsonProperty("file_keys")
    private List<String> fileKeys;

    public static NewsResponse from(News news) {
        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getCreatedDate(),
                news.getFiles().stream()
                        .map(file -> file.getFileKey())
                        .toList()
        );
    }
}
