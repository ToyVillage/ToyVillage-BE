package com.command.toyvillage_server.domain.web.file.presentation.dto.response;

import com.command.toyvillage_server.domain.web.file.domain.File;
import lombok.Builder;

@Builder
public record FileResponse(
    String fileName,
    String fileKey
) {
    public static FileResponse from(File file) {
        return FileResponse.builder()
            .fileName(file.getFileName())
            .fileKey(file.getFileKey())
            .build();
    }
}
