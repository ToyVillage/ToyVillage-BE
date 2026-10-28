package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.command.toyvillage_server.domain.app.excel.service.ExcelSheetSupport.*;

@Service
@RequiredArgsConstructor
public class ExportWorkReportExcelService implements ExcelSheetService {

    private final WorkReportRepository workReportRepository;

    @Override
    public ExcelType type() {
        return ExcelType.WORK_REPORT;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "작성일", "업무 제목", "작성자", "내용", "비고", "상태", "반려사유");
        forEachPage(page -> workReportRepository.findByCreatedAtBetween(
            startTime(startDate), endTime(endDate), page(page, "createdAt")), reports -> {
            for (WorkReport w : reports) {
                row(sheet, w.getCreatedAt().toLocalDate(), w.getTask().getTitle(), w.getAppAdmin().getName(),
                    w.getContent(), w.getNote(), reportStatus(w.getStatus().name()), w.getRejectionReason());
            }
        });
    }
}
