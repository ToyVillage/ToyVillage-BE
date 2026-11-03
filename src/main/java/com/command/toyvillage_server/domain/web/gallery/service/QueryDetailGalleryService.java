package com.command.toyvillage_server.domain.web.gallery.service;

import com.command.toyvillage_server.domain.web.gallery.domain.Gallery;
import com.command.toyvillage_server.domain.web.gallery.domain.repository.GalleryRepository;
import com.command.toyvillage_server.domain.web.gallery.exception.GalleryNotFoundException;
import com.command.toyvillage_server.domain.web.gallery.presentation.dto.response.GalleryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QueryDetailGalleryService {
    private final GalleryRepository galleryRepository;

    @Transactional(readOnly = true)
    public GalleryResponse execute(Long galleryId) {
        Gallery gallery = galleryRepository.findById(galleryId).orElseThrow(() -> GalleryNotFoundException.EXCEPTION);

        return GalleryResponse.from(gallery);
    }
}
