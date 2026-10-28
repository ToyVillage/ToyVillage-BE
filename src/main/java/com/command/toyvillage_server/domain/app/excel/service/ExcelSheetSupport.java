package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class ExcelSheetSupport {

    static final int PAGE_SIZE = 200;
    private static final int MAX_DATA_ROWS = 10_000;
    private static final LocalDate MIN_DATE = LocalDate.of(1000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);

    private ExcelSheetSupport() {
    }

    static <T> void forEachPage(IntFunction<List<T>> fetch, Consumer<List<T>> write) {
        for (int page = 0; ; page++) {
            List<T> items = fetch.apply(page);
            if (items.isEmpty()) return;
            write.accept(items);
            if (items.size() < PAGE_SIZE) return;
        }
    }

    static PageRequest page(int number, String dateField) {
        return PageRequest.of(number, PAGE_SIZE, Sort.by(dateField).ascending().and(Sort.by("id").ascending()));
    }

    static LocalDate start(LocalDate value) {
        return value == null ? MIN_DATE : value;
    }

    static LocalDate end(LocalDate value) {
        return value == null ? MAX_DATE : value;
    }

    static LocalDateTime startTime(LocalDate value) {
        return start(value).atStartOfDay();
    }

    static LocalDateTime endTime(LocalDate value) {
        return end(value).atTime(23, 59, 59, 999_999_000);
    }

    static void header(XSSFSheet sheet, String... titles) {
        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Row row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
    }

    static Row row(XSSFSheet sheet, Object... values) {
        int index = sheet.getLastRowNum() + 1;
        if (index > MAX_DATA_ROWS) {
            throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
        }
        Row row = sheet.createRow(index);
        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(i);
            Object v = values[i];
            if (v instanceof Number n) {
                cell.setCellValue(n.doubleValue());
            } else if (v instanceof LocalDate d) {
                cell.setCellValue(d);
                cell.setCellStyle(dateStyle(sheet.getWorkbook()));
            } else if (v != null) {
                cell.setCellValue(Objects.toString(v));
            }
        }
        return row;
    }

    static String reportStatus(String value) {
        return switch (value) {
            case "PENDING" -> "대기";
            case "APPROVED" -> "승인";
            case "REJECTED" -> "반려";
            case "MISSING" -> "미제출";
            case "RESUBMIT" -> "재제출";
            default -> value;
        };
    }

    private static CellStyle dateStyle(XSSFWorkbook workbook) {
        short format = workbook.createDataFormat().getFormat("yyyy-mm-dd");
        for (int i = 0; i < workbook.getNumCellStyles(); i++) {
            CellStyle style = workbook.getCellStyleAt(i);
            if (style.getDataFormat() == format) {
                return style;
            }
        }
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(format);
        return style;
    }
}
