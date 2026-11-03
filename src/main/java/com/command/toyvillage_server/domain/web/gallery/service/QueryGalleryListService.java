package com.command.toyvillage_server.domain.web.gallery.service;

import com.command.toyvillage_server.domain.web.gallery.domain.repository.GalleryRepository;
import com.command.toyvillage_server.domain.web.gallery.presentation.dto.response.GalleryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryGalleryListService {
    private final GalleryRepository galleryRepository;

    @Transactional(readOnly = true)
    public List<GalleryResponse> execute(Pageable p) {
        Pageable pageable = PageRequest.of(
            p.getPageNumber(),
            p.getPageSize(),
            Sort.by(Sort.Direction.DESC, "id")
        );

        return galleryRepository.findAllWithFile(pageable)
            .map(GalleryResponse::from)
            .toList();
    }
}
