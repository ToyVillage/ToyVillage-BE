package com.command.toyvillage_server.domain.app.document.presentation.dto.response;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DocumentDetailResponse(
    Long id,
    String title,
    DocumentType type,
    LocalDateTime createdAt,
    List<FileResponse> files
) {
    public static DocumentDetailResponse from(Document document) {
        return DocumentDetailResponse.builder()
            .id(document.getId())
            .title(document.getTitle())
            .type(document.getType())
            .createdAt(document.getCreatedAt())
            .files(
                document.getFiles().stream()
                    .map(FileResponse::from)
                    .toList()
            )
            .build();
    }
}
