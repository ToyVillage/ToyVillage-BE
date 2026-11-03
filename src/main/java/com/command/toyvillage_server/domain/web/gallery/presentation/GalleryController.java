package com.command.toyvillage_server.domain.web.gallery.presentation;

import com.command.toyvillage_server.domain.web.gallery.presentation.dto.request.GalleryRequest;
import com.command.toyvillage_server.domain.web.gallery.presentation.dto.response.GalleryResponse;
import com.command.toyvillage_server.domain.web.gallery.service.*;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/gallery")
@RestController
public class GalleryController {
    private final CreateGalleryService createGalleryService;
    private final UpdateGalleryService updateGalleryService;
    private final DeleteGalleryService deleteGalleryService;
    private final QueryDetailGalleryService queryDetailGalleryService;
    private final QueryGalleryListService queryGalleryListService;

    @PostMapping
    public ResponseEntity<MessageResponse> post(@Valid @RequestBody GalleryRequest request) {
        Long id = createGalleryService.execute(request);
        return ResponseEntity.created(URI.create("/gallery/" + id))
            .body(MessageResponse.of("갤러리가 추가되었습니다."));
    }

    @GetMapping
    public List<GalleryResponse> getList(Pageable pageable) {
        return queryGalleryListService.execute(pageable);
    }

    @GetMapping("/{gallery-id}")
    public GalleryResponse get(@PathVariable("gallery-id") Long galleryId) {
        return queryDetailGalleryService.execute(galleryId);
    }

    @PutMapping("/{gallery_id}")
    public MessageResponse put(@Valid @RequestBody GalleryRequest request, @PathVariable("gallery_id") Long galleryId) {
        updateGalleryService.execute(request, galleryId);
        return MessageResponse.of("갤러리가 수정되었습니다.");
    }

    @DeleteMapping("/{gallery_id}")
    public MessageResponse delete(@PathVariable("gallery_id") Long galleryId) {
        deleteGalleryService.execute(galleryId);
        return MessageResponse.of("갤러리가 삭제되었습니다.");
    }
}
