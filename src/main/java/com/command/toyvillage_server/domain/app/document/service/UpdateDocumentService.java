package com.command.toyvillage_server.domain.app.document.service;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.repository.DocumentRepository;
import com.command.toyvillage_server.domain.app.document.exception.DocumentNotFoundException;
import com.command.toyvillage_server.domain.app.document.presentation.dto.request.DocumentRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateDocumentService {
    private final DocumentRepository documentRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long id, DocumentRequest request) {
        Document document = documentRepository.findById(id).orElseThrow(() -> DocumentNotFoundException.EXCEPTION);
        List<File> files = fileRepository.findAllByFileKeyIn(request.getFiles());
        if (files.size() != request.getFiles().size())
            throw FileNotFoundException.EXCEPTION;

        document.update(request.getTitle(), request.getType(), files);
    }
}
