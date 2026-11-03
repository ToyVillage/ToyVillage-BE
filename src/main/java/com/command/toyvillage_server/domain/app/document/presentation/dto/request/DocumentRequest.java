package com.command.toyvillage_server.domain.app.document.presentation.dto.request;

import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class DocumentRequest {
    @NotBlank(message = "자료 제목은 비어있을 수 없습니다.")
    private String title;

    @NotNull(message = "자료 종류는 선택되어야합니다.")
    private DocumentType type;

    @NotEmpty(message = "파일이 비어있을 수 없습니다.")
    private List<String> files;
}
