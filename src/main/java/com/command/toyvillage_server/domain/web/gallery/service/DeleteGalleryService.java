package com.command.toyvillage_server.domain.web.gallery.service;

import com.command.toyvillage_server.domain.web.gallery.domain.Gallery;
import com.command.toyvillage_server.domain.web.gallery.domain.repository.GalleryRepository;
import com.command.toyvillage_server.domain.web.gallery.exception.GalleryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteGalleryService {
    private final GalleryRepository galleryRepository;

    @Transactional
    public void execute(Long id) {
        Gallery gallery = galleryRepository.findById(id).orElseThrow(() -> GalleryNotFoundException.EXCEPTION);
        galleryRepository.delete(gallery);
    }
}
