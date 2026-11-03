package com.command.toyvillage_server.domain.app.document.presentation;

import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import com.command.toyvillage_server.domain.app.document.presentation.dto.request.DocumentRequest;
import com.command.toyvillage_server.domain.app.document.presentation.dto.response.DocumentDetailResponse;
import com.command.toyvillage_server.domain.app.document.presentation.dto.response.DocumentListResponse;
import com.command.toyvillage_server.domain.app.document.service.*;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final CreateDocumentService createDocumentService;
    private final QueryDocumentDetailService queryDocumentDetailService;
    private final QueryDocumentListService queryDocumentListService;
    private final UpdateDocumentService updateDocumentService;
    private final DeleteDocumentService deleteDocumentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody DocumentRequest request) {
        Long id = createDocumentService.execute(request);

        return ResponseEntity.created(URI.create("/documents/" + id))
            .body(MessageResponse.of("자료 등록 성공"));
    }

    @GetMapping("/{id}")
    public DocumentDetailResponse getDetail(@PathVariable Long id) {
        return queryDocumentDetailService.execute(id);
    }

    @GetMapping
    public DocumentListResponse getList(
        @RequestParam(required = false, defaultValue = "") String keyword,
        @RequestParam(required = false) List<DocumentType> types,
        Pageable pageable
    ) {
        return queryDocumentListService.execute(keyword, types, pageable);
    }

    @PutMapping("/{id}")
    public MessageResponse update(@PathVariable Long id, @Valid @RequestBody DocumentRequest request) {
        updateDocumentService.execute(id, request);
        return MessageResponse.of("자료 수정 성공");
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable Long id) {
        deleteDocumentService.execute(id);
        return MessageResponse.of("자료 삭제 성공");
    }

}
