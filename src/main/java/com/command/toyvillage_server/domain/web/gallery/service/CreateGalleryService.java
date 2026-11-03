package com.command.toyvillage_server.domain.web.gallery.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.gallery.domain.Gallery;
import com.command.toyvillage_server.domain.web.gallery.domain.repository.GalleryRepository;
import com.command.toyvillage_server.domain.web.gallery.presentation.dto.request.GalleryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateGalleryService {
    private final GalleryRepository galleryRepository;
    private final FileRepository fileRepository;

    @Transactional
    public Long execute(GalleryRequest request) {
        File file = fileRepository.findByFileKey(request.getFileKey()).orElseThrow(() -> FileNotFoundException.EXCEPTION);
        Gallery gallery = Gallery.builder()
            .title(request.getGalleryTitle())
            .file(file)
            .build();

        return galleryRepository.save(gallery).getId();
    }
}
