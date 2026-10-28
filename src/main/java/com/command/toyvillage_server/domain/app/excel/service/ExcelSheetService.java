package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.time.LocalDate;

public interface ExcelSheetService {
    ExcelType type();

    void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate);
}
