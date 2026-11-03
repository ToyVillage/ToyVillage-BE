package com.command.toyvillage_server.domain.app.document.presentation.dto.response;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DocumentListResponse(
    List<DocumentResponse> documents,
    int totalPageSize
) {

    public static DocumentListResponse from(Page<Document> documents) {
        return DocumentListResponse.builder()
            .documents(documents.map(DocumentResponse::from).toList())
            .totalPageSize(documents.getTotalPages())
            .build();
    }

    @Builder
    private record DocumentResponse(
        Long id,
        String title,
        DocumentType type,
        LocalDateTime createdAt
    ) {
        public static DocumentResponse from(Document document) {
            return DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .type(document.getType())
                .createdAt(document.getCreatedAt())
                .build();
        }
    }

}
