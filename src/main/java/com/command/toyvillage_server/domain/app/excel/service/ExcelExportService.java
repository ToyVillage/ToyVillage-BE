package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelExportMode;
import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private static final LocalDate MIN_DATE = LocalDate.of(1000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);
    private static final int THUMBNAIL_SIZE = 120;
    private static final long MAX_IMAGE_PIXELS = 20_000_000;

    private final ExportNoticeExcelService exportNoticeExcelService;
    private final ExportTaskExcelService exportTaskExcelService;
    private final ExportWorkReportExcelService exportWorkReportExcelService;
    private final ExportReservationExcelService exportReservationExcelService;
    private final ExportTeamMemberExcelService exportTeamMemberExcelService;
    private final ExportAnimalExcelService exportAnimalExcelService;
    private final ExportWorkLogExcelService exportWorkLogExcelService;

    @Transactional(readOnly = true)
    public byte[] execute(List<ExcelType> types, ExcelExportMode mode, LocalDate startDate, LocalDate endDate) {
        LocalDate start = startDate == null ? MIN_DATE : startDate;
        LocalDate end = endDate == null ? MAX_DATE : endDate;
        try {
            return switch (mode) {
                case SHEET -> workbook(types, start, end);
                case ZIP -> zip(types, start, end);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ExcelSheet export(ExcelType type, LocalDate start, LocalDate end) {
        return switch (type) {
            case NOTICE -> exportNoticeExcelService.execute(start, end);
            case TASK -> exportTaskExcelService.execute(start, end);
            case WORK_REPORT -> exportWorkReportExcelService.execute(start, end);
            case RESERVATION -> exportReservationExcelService.execute(start, end);
            case TEAM_MEMBER -> exportTeamMemberExcelService.execute();
            case ANIMAL -> exportAnimalExcelService.execute();
            case WORK_LOG -> exportWorkLogExcelService.execute(start, end);
        };
    }

    private byte[] zip(List<ExcelType> types, LocalDate start, LocalDate end) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(out)) {
            for (ExcelType type : types) {
                zip.putNextEntry(new ZipEntry(type.getTitle() + ".xlsx"));
                zip.write(workbook(List.of(type), start, end));
                zip.closeEntry();
            }
        }
        return out.toByteArray();
    }

    private byte[] workbook(List<ExcelType> types, LocalDate start, LocalDate end) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = headerStyle(workbook);
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));

            for (ExcelType type : types) {
                ExcelSheet data = export(type, start, end);
                XSSFSheet sheet = workbook.createSheet(type.getTitle());
                writeHeader(sheet, data.headers(), headerStyle);
                for (int i = 0; i < data.rows().size(); i++) {
                    writeRow(sheet, i + 1, data.rows().get(i), dateStyle);
                }
                for (int i = 0; i < data.headers().size(); i++) {
                    if (sheet.getColumnWidth(i) == sheet.getDefaultColumnWidth() * 256) {
                        sheet.setColumnWidth(i, 20 * 256);
                    }
                }
                sheet.createFreezePane(0, 1);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private CellStyle headerStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void writeHeader(XSSFSheet sheet, List<String> headers, CellStyle style) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(style);
        }
    }

    private void writeRow(XSSFSheet sheet, int index, List<Object> values, CellStyle dateStyle) {
        Row row = sheet.createRow(index);
        for (int i = 0; i < values.size(); i++) {
            Cell cell = row.createCell(i);
            Object v = values.get(i);
            if (v instanceof byte[] image) {
                writeImage(sheet, row, i, image);
            } else if (v instanceof Number n) {
                cell.setCellValue(n.doubleValue());
            } else if (v instanceof LocalDate d) {
                cell.setCellValue(d);
                cell.setCellStyle(dateStyle);
            } else if (v != null) {
                cell.setCellValue(Objects.toString(v));
            }
        }
    }

    private void writeImage(XSSFSheet sheet, Row row, int column, byte[] image) {
        Thumbnail thumbnail = thumbnail(image);
        if (thumbnail == null) {
            row.getCell(column).setCellValue("이미지 없음");
            return;
        }
        row.setHeightInPoints(100);
        sheet.setColumnWidth(column, 18 * 256);
        int pictureIdx = sheet.getWorkbook().addPicture(thumbnail.data(), Workbook.PICTURE_TYPE_PNG);
        XSSFClientAnchor anchor = new XSSFClientAnchor();
        anchor.setCol1(column);
        anchor.setRow1(row.getRowNum());
        anchor.setDx1(4 * Units.EMU_PER_PIXEL);
        anchor.setDy1(4 * Units.EMU_PER_PIXEL);
        anchor.setCol2(column);
        anchor.setRow2(row.getRowNum());
        anchor.setDx2((4 + thumbnail.width()) * Units.EMU_PER_PIXEL);
        anchor.setDy2((4 + thumbnail.height()) * Units.EMU_PER_PIXEL);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        drawing.createPicture(anchor, pictureIdx);
    }

    private record Thumbnail(byte[] data, int width, int height) {}

    private Thumbnail thumbnail(byte[] original) {
        try (ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(original))) {
            if (input == null) {
                return null;
            }
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) return null;
            ImageReader reader = readers.next();
            BufferedImage image;
            try {
                reader.setInput(input);
                if ((long) reader.getWidth(0) * reader.getHeight(0) > MAX_IMAGE_PIXELS) return null;
                image = reader.read(0);
            } finally {
                reader.dispose();
            }
            double scale = Math.min(1.0, (double) THUMBNAIL_SIZE / Math.max(image.getWidth(), image.getHeight()));
            int w = Math.max(1, (int) (image.getWidth() * scale));
            int h = Math.max(1, (int) (image.getHeight() * scale));
            BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(image, 0, 0, w, h, null);
            g.dispose();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(resized, "png", out);
            return new Thumbnail(out.toByteArray(), w, h);
        } catch (IOException | RuntimeException e) {
            return null;
        }
    }
}
