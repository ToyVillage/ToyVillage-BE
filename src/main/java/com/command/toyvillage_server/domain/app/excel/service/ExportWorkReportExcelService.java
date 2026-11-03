package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportWorkReportExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;
    private static final List<String> HEADERS = List.of("작성일시", "업무 제목", "작성자", "내용", "비고", "상태", "반려사유");

    private final WorkReportRepository workReportRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<WorkReport> reports = workReportRepository.findByCreatedAtBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59, 999_999_000),
                PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt", "id")));
            for (WorkReport w : reports) {
                rows.add(Arrays.asList(w.getCreatedAt(), w.getTask().getTitle(), w.getAppAdmin().getName(),
                    w.getContent(), w.getNote(), reportStatus(w.getStatus()), w.getRejectionReason()));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (reports.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }

    private String reportStatus(Status value) {
        return switch (value) {
            case PENDING -> "대기";
            case APPROVED -> "승인";
            case REJECTED -> "반려";
            case MISSING -> "미제출";
            case RESUBMIT -> "재제출";
        };
    }
}
