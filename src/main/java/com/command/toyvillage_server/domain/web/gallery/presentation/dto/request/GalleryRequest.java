package com.command.toyvillage_server.domain.web.gallery.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GalleryRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    @JsonProperty("gallery_title")
    private String galleryTitle;

    @NotBlank
    @JsonProperty("file_key")
    private String fileKey;
}
