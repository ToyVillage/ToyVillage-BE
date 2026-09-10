package com.command.toyvillage_server.domain.web.file.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FileFacade {
    private final FileRepository fileRepository;

    public List<File> findAllByKeys(List<String> fileKeys) {
        if (fileKeys == null) {
            return List.of();
        }

        List<String> distinctKeys = fileKeys.stream().distinct().toList();
        if (distinctKeys.isEmpty()) {
            return List.of();
        }

        List<File> files = fileRepository.findAllByFileKeyIn(distinctKeys);
        if (files.size() != distinctKeys.size()) {
            throw FileNotFoundException.EXCEPTION;
        }
        return files;
    }
}
