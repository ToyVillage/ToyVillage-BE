package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelFiles;
import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import com.command.toyvillage_server.domain.app.document.domain.repository.DocumentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportDocumentExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;
    private static final List<String> HEADERS = List.of("작성일시", "제목", "유형", "첨부파일");

    private final DocumentRepository documentRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<Document> documents = documentRepository.findExportPage(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59, 999_999_000),
                PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt", "id")));
            for (Document d : documents) {
                rows.add(Arrays.asList(d.getCreatedAt(), d.getTitle(), type(d.getType()),
                    d.getFiles().isEmpty() ? null : new ExcelFiles(d.getFiles())));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (documents.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }

    private String type(DocumentType value) {
        return switch (value) {
            case PNG -> "PNG";
            case JPG -> "JPG";
            case PDF -> "PDF";
            case OTHER -> "기타";
        };
    }
}
