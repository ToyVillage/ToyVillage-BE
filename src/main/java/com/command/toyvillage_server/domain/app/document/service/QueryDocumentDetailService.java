package com.command.toyvillage_server.domain.app.document.service;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.repository.DocumentRepository;
import com.command.toyvillage_server.domain.app.document.exception.DocumentNotFoundException;
import com.command.toyvillage_server.domain.app.document.presentation.dto.response.DocumentDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QueryDocumentDetailService {
    private final DocumentRepository documentRepository;

    @Transactional(readOnly = true)
    public DocumentDetailResponse execute(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> DocumentNotFoundException.EXCEPTION);

        return DocumentDetailResponse.from(document);
    }
}
