package com.command.toyvillage_server.domain.app.document.service;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import com.command.toyvillage_server.domain.app.document.domain.repository.DocumentRepository;
import com.command.toyvillage_server.domain.app.document.presentation.dto.response.DocumentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryDocumentListService {
    private final DocumentRepository documentRepository;

    @Transactional(readOnly = true)
    public DocumentListResponse execute(String keyword, List<DocumentType> types, Pageable p) {
        Pageable pageable = PageRequest.of(
            p.getPageNumber(),
            p.getPageSize(),
            Sort.by(
                p.getSort().stream()
                    .findFirst()
                    .map(Sort.Order::getDirection)
                    .orElse(Sort.Direction.DESC),
                "createdAt"
            )
        );

        Page<Document> documents = types == null || types.isEmpty()
            ? documentRepository.getAllByTitleContainsIgnoreCase(keyword, pageable)
            : documentRepository.getAllByTitleContainsIgnoreCaseAndTypeIn(keyword, types, pageable);

        return DocumentListResponse.from(documents);
    }
}
