package com.command.toyvillage_server.domain.app.document.service;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.repository.DocumentRepository;
import com.command.toyvillage_server.domain.app.document.exception.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteDocumentService {
    private final DocumentRepository documentRepository;

    @Transactional
    public void execute(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> DocumentNotFoundException.EXCEPTION);

        documentRepository.delete(document);
    }
}
