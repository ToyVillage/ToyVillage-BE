package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelExportMode;
import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelFiles;
import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private static final LocalDate MIN_DATE = LocalDate.of(1000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);
    private static final int THUMBNAIL_SIZE = 120;
    private static final long MAX_IMAGE_PIXELS = 20_000_000;
    private static final long MAX_ATTACHMENT_BYTES = 200L * 1024 * 1024;
    private static final int ICON_WIDTH = 96;
    private static final int ICON_HEIGHT = 72;

    private final ExportNoticeExcelService exportNoticeExcelService;
    private final ExportTaskExcelService exportTaskExcelService;
    private final ExportWorkReportExcelService exportWorkReportExcelService;
    private final ExportReservationExcelService exportReservationExcelService;
    private final ExportTeamMemberExcelService exportTeamMemberExcelService;
    private final ExportAnimalExcelService exportAnimalExcelService;
    private final ExportWorkLogExcelService exportWorkLogExcelService;
    private final ExportFeedLogExcelService exportFeedLogExcelService;
    private final ExportFifthFloorFeedLogExcelService exportFifthFloorFeedLogExcelService;
    private final ExportAnimalObservationExcelService exportAnimalObservationExcelService;
    private final ExportDocumentExcelService exportDocumentExcelService;
    private final AwsS3Provider awsS3Provider;

    @Transactional(readOnly = true)
    public byte[] execute(List<ExcelType> types, ExcelExportMode mode, LocalDate startDate, LocalDate endDate) {
        LocalDate start = startDate == null ? MIN_DATE : startDate;
        LocalDate end = endDate == null ? MAX_DATE : endDate;
        AtomicLong downloaded = new AtomicLong();
        try {
            return switch (mode) {
                case SHEET -> workbook(types, start, end, null, downloaded);
                case ZIP -> zip(types, start, end, downloaded);
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
            case FEED_LOG -> exportFeedLogExcelService.execute(start, end);
            case FIFTH_FLOOR_FEED_LOG -> exportFifthFloorFeedLogExcelService.execute(start, end);
            case ANIMAL_OBSERVATION -> exportAnimalObservationExcelService.execute(start, end);
            case DOCUMENT -> exportDocumentExcelService.execute(start, end);
        };
    }

    private byte[] zip(List<ExcelType> types, LocalDate start, LocalDate end, AtomicLong downloaded) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(out)) {
            for (ExcelType type : types) {
                Map<String, byte[]> attachments = new LinkedHashMap<>();
                zip.putNextEntry(new ZipEntry(type.getTitle() + ".xlsx"));
                zip.write(workbook(List.of(type), start, end, attachments, downloaded));
                zip.closeEntry();
                for (Map.Entry<String, byte[]> attachment : attachments.entrySet()) {
                    zip.putNextEntry(new ZipEntry(attachment.getKey()));
                    zip.write(attachment.getValue());
                    zip.closeEntry();
                }
            }
        }
        return out.toByteArray();
    }

    // attachments가 null이면 첨부파일을 엑셀 안에 OLE 객체로 넣고, 아니면 ZIP에 함께 담을 파일을 경로별로 모은다.
    private byte[] workbook(List<ExcelType> types, LocalDate start, LocalDate end,
                            Map<String, byte[]> attachments, AtomicLong downloaded) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = headerStyle(workbook);
            CellStyles styles = cellStyles(workbook);

            for (ExcelType type : types) {
                ExcelSheet data = export(type, start, end);
                XSSFSheet sheet = workbook.createSheet(type.getTitle());
                writeHeader(sheet, data.headers(), headerStyle);
                for (int i = 0; i < data.rows().size(); i++) {
                    List<Object> values = data.rows().get(i);
                    if (values.size() != data.headers().size()) {
                        throw new IllegalStateException(type + " 행의 값 개수가 헤더 개수와 다릅니다.");
                    }
                    writeRow(sheet, i + 1, values, styles, type.getTitle(), attachments, downloaded);
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

    private record CellStyles(CellStyle date, CellStyle dateTime, CellStyle time) {}

    private CellStyles cellStyles(XSSFWorkbook workbook) {
        return new CellStyles(
            formatStyle(workbook, "yyyy-mm-dd"),
            formatStyle(workbook, "yyyy-mm-dd hh:mm"),
            formatStyle(workbook, "hh:mm")
        );
    }

    private CellStyle formatStyle(XSSFWorkbook workbook, String format) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(format));
        return style;
    }

    private void writeRow(XSSFSheet sheet, int index, List<Object> values, CellStyles styles, String title,
                          Map<String, byte[]> attachments, AtomicLong downloaded) throws IOException {
        Row row = sheet.createRow(index);
        for (int i = 0; i < values.size(); i++) {
            Cell cell = row.createCell(i);
            Object v = values.get(i);
            // byte[] 값은 이미지 원본으로 보고 썸네일로 변환해 셀 위치에 삽입한다.
            if (v instanceof byte[] image) {
                writeImage(sheet, row, i, image);
            } else if (v instanceof ExcelFiles files) {
                if (attachments == null) {
                    embedFiles(sheet, row, i, files, downloaded);
                } else {
                    cell.setCellValue(collectFiles(files, "첨부파일/" + title + "/" + (index + 1) + "행", attachments, downloaded));
                }
            } else if (v instanceof Float f) {
                cell.setCellValue(Double.parseDouble(f.toString()));
            } else if (v instanceof Number n) {
                cell.setCellValue(n.doubleValue());
            } else if (v instanceof LocalDateTime dt) {
                cell.setCellValue(dt);
                cell.setCellStyle(styles.dateTime());
            } else if (v instanceof LocalDate d) {
                cell.setCellValue(d);
                cell.setCellStyle(styles.date());
            } else if (v instanceof LocalTime t) {
                cell.setCellValue(t.toSecondOfDay() / 86_400.0);
                cell.setCellStyle(styles.time());
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

    private String collectFiles(ExcelFiles files, String folder, Map<String, byte[]> attachments,
                                AtomicLong downloaded) {
        List<String> paths = new ArrayList<>();
        for (File file : files.files()) {
            byte[] data = downloadAttachment(file, downloaded);
            if (data == null) {
                paths.add(file.getFileName() + " (다운로드 실패)");
                continue;
            }
            String fileName = file.getFileName().replaceAll("[\\\\/]", "_");
            String path = folder + "/" + fileName;
            for (int n = 1; attachments.containsKey(path); n++) {
                path = folder + "/" + n + "_" + fileName;
            }
            attachments.put(path, data);
            paths.add(path);
        }
        return String.join(", ", paths);
    }

    private byte[] downloadAttachment(File file, AtomicLong downloaded) {
        byte[] data = awsS3Provider.download(file.getFileKey());
        if (data != null && downloaded.addAndGet(data.length) > MAX_ATTACHMENT_BYTES) {
            throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_ATTACHMENT_TOO_LARGE);
        }
        return data;
    }

    private void embedFiles(XSSFSheet sheet, Row row, int column, ExcelFiles files, AtomicLong downloaded)
        throws IOException {
        XSSFWorkbook workbook = sheet.getWorkbook();
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        List<String> failed = new ArrayList<>();
        int x = 4;
        for (File file : files.files()) {
            byte[] data = downloadAttachment(file, downloaded);
            if (data == null) {
                failed.add(file.getFileName());
                continue;
            }
            int iconIdx = workbook.addPicture(fileIcon(file.getFileName()), Workbook.PICTURE_TYPE_PNG);
            int storageId = workbook.addOlePackage(data, file.getFileName(), file.getFileName(), file.getFileName());
            XSSFClientAnchor anchor = new XSSFClientAnchor();
            anchor.setCol1(column);
            anchor.setRow1(row.getRowNum());
            anchor.setDx1(x * Units.EMU_PER_PIXEL);
            anchor.setDy1(4 * Units.EMU_PER_PIXEL);
            anchor.setCol2(column);
            anchor.setRow2(row.getRowNum());
            anchor.setDx2((x + ICON_WIDTH) * Units.EMU_PER_PIXEL);
            anchor.setDy2((4 + ICON_HEIGHT) * Units.EMU_PER_PIXEL);
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            drawing.createObjectData(anchor, storageId, iconIdx);
            x += ICON_WIDTH + 4;
        }
        if (x > 4) {
            row.setHeightInPoints(Math.max(row.getHeightInPoints(), (ICON_HEIGHT + 8) * 0.75f));
            sheet.setColumnWidth(column, Math.max(sheet.getColumnWidth(column), (x / 7 + 2) * 256));
        }
        if (!failed.isEmpty()) {
            row.getCell(column).setCellValue("다운로드 실패: " + String.join(", ", failed));
        }
    }

    private byte[] fileIcon(String fileName) throws IOException {
        int dot = fileName.lastIndexOf('.');
        String extension = dot < 0 ? "FILE" : fileName.substring(dot + 1).toUpperCase();
        BufferedImage icon = new BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = icon.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ICON_WIDTH, ICON_HEIGHT);
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(2));
        g.drawRect(1, 1, ICON_WIDTH - 3, ICON_HEIGHT - 3);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 18));
        drawCentered(g, extension, 32);
        java.awt.Font nameFont = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 11);
        // 서버에 한글 폰트가 없으면 파일 이름이 깨지므로 표시할 수 있을 때만 그린다.
        if (nameFont.canDisplayUpTo(fileName) == -1) {
            g.setFont(nameFont);
            drawCentered(g, fileName.length() > 14 ? fileName.substring(0, 13) + "…" : fileName, 56);
        }
        g.dispose();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(icon, "png", out);
        return out.toByteArray();
    }

    private void drawCentered(Graphics2D g, String text, int y) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, (ICON_WIDTH - metrics.stringWidth(text)) / 2, y);
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
