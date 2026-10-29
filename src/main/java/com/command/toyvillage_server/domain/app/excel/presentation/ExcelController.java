package com.command.toyvillage_server.domain.app.excel.presentation;

import com.command.toyvillage_server.domain.app.excel.service.ExcelExportService;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelExportService excelExportService;

    @GetMapping
    public ResponseEntity<byte[]> export(
        @RequestParam List<ExcelType> types,
        @RequestParam(defaultValue = "SHEET") ExcelExportMode mode,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<ExcelType> distinctTypes = types.stream().filter(Objects::nonNull).distinct().toList();
        if (distinctTypes.isEmpty()) {
            throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TYPE_REQUIRED);
        }
        if (distinctTypes.stream().noneMatch(ExcelType::isDated)) {
            startDate = null;
            endDate = null;
        }
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_INVALID_DATE_RANGE);
        }

        byte[] body = excelExportService.execute(distinctTypes, mode, startDate, endDate);

        String fileName = (distinctTypes.size() == 1 ? distinctTypes.get(0).getTitle() : "통합")
            + (startDate != null ? "_" + startDate : "")
            + (endDate != null ? "_" + endDate : "")
            + "." + mode.getExtension();

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build().toString())
            .contentType(MediaType.parseMediaType(mode.getContentType()))
            .body(body);
    }
}
