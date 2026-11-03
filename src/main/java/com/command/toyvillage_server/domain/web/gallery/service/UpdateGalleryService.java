package com.command.toyvillage_server.domain.web.gallery.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.gallery.domain.Gallery;
import com.command.toyvillage_server.domain.web.gallery.domain.repository.GalleryRepository;
import com.command.toyvillage_server.domain.web.gallery.exception.GalleryNotFoundException;
import com.command.toyvillage_server.domain.web.gallery.presentation.dto.request.GalleryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateGalleryService {
    private final GalleryRepository galleryRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(GalleryRequest request, Long id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> GalleryNotFoundException.EXCEPTION);
        File file = fileRepository.findByFileKey(request.getFileKey()).orElseThrow(() -> FileNotFoundException.EXCEPTION);

        gallery.update(
            request.getGalleryTitle(),
            file
        );
    }
}
