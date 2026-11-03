package com.command.toyvillage_server.domain.web.gallery.presentation.dto.response;

import com.command.toyvillage_server.domain.web.gallery.domain.Gallery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GalleryResponse (
    @JsonProperty("gallery_id")
    Long galleryId,
    @JsonProperty("gallery_title")
    String galleryTitle,
    @JsonProperty("gallery_file_key")
    String galleryFileKey
) {
    public static GalleryResponse from(Gallery gallery) {
        return GalleryResponse.builder()
            .galleryId(gallery.getId())
            .galleryTitle(gallery.getTitle())
            .galleryFileKey(gallery.getFile().getFileKey())
            .build();
    }
}
