package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelExportService {

    private final Map<ExcelType, ExcelSheetService> sheetServices = new EnumMap<>(ExcelType.class);

    public ExcelExportService(List<ExcelSheetService> sheetServices) {
        sheetServices.forEach(service -> this.sheetServices.put(service.type(), service));
    }

    @Transactional(readOnly = true)
    public byte[] execute(ExcelType type, LocalDate startDate, LocalDate endDate) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(type.getTitle());

            sheetServices.get(type).execute(sheet, startDate, endDate);

            for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                if (sheet.getColumnWidth(i) == sheet.getDefaultColumnWidth() * 256) {
                    sheet.setColumnWidth(i, 20 * 256);
                }
            }
            sheet.createFreezePane(0, 1);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
