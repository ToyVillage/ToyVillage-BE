package com.command.toyvillage_server.domain.web.file.presentation;

import com.command.toyvillage_server.domain.web.file.presentation.dto.response.FileUploadResponse;
import com.command.toyvillage_server.domain.web.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileUploadService fileUploadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResponse upload(@RequestParam("files") MultipartFile file) {
        return fileUploadService.execute(file);
    }
}
