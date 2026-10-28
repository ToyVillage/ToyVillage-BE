package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
import java.util.Map;
import java.util.stream.Collectors;

import static com.command.toyvillage_server.domain.app.excel.service.ExcelSheetSupport.*;

@Service
@RequiredArgsConstructor
public class ExportAnimalExcelService implements ExcelSheetService {

    private static final int THUMBNAIL_SIZE = 120;
    private static final long MAX_IMAGE_PIXELS = 20_000_000;
    private static final int MAX_ANIMAL_ROWS = 1_000;

    private final AnimalManageRepository animalManageRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final AwsS3Provider awsS3Provider;

    @Override
    public ExcelType type() {
        return ExcelType.ANIMAL;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "사진", "개체명", "종", "영문명", "학명", "분류", "세부종", "성별", "출생년도", "법적지위", "기타정보");
        sheet.setColumnWidth(0, 18 * 256);
        XSSFDrawing drawing = sheet.createDrawingPatriarch();

        forEachPage(page -> animalManageRepository.findExportPage(PageRequest.of(page, PAGE_SIZE)), animals -> {
            List<Long> kindIds = animals.stream().map(a -> a.getAnimalKind().getId()).distinct().toList();
            Map<Long, String> legalByKind = animalLegalDesignationRepository.findAllByAnimalKind_IdIn(kindIds).stream()
                .collect(Collectors.groupingBy(d -> d.getAnimalKind().getId(),
                    Collectors.mapping(d -> d.getAnimalLegalStatus().getKind(), Collectors.joining(", "))));
            for (AnimalManage a : animals) {
                if (sheet.getLastRowNum() >= MAX_ANIMAL_ROWS) {
                    throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
                }
                AnimalKind kind = a.getAnimalKind();
                Row row = row(sheet, null, a.getAnimalName(), kind.getKindName(), kind.getEngName(), kind.getScientificName(),
                    kind.getAnimalTaxonomic().getType(), kind.getDetailKind(), gender(a.getAnimalGender().name()),
                    a.getBirthYear(), legalByKind.getOrDefault(kind.getId(), ""), a.getOtherInfo());
                row.setHeightInPoints(100);

                Thumbnail thumbnail = a.getAnimalImage() == null ? null : thumbnail(awsS3Provider.download(a.getAnimalImage().getFileKey()));
                if (thumbnail == null) {
                    row.getCell(0).setCellValue("이미지 없음");
                } else {
                    insertPicture(sheet, drawing, row.getRowNum(), thumbnail);
                }
            }
        });
    }

    private void insertPicture(XSSFSheet sheet, XSSFDrawing drawing, int rowIndex, Thumbnail thumbnail) {
        int pictureIdx = sheet.getWorkbook().addPicture(thumbnail.data(), Workbook.PICTURE_TYPE_PNG);
        XSSFClientAnchor anchor = new XSSFClientAnchor();
        anchor.setCol1(0);
        anchor.setRow1(rowIndex);
        anchor.setDx1(4 * Units.EMU_PER_PIXEL);
        anchor.setDy1(4 * Units.EMU_PER_PIXEL);
        anchor.setCol2(0);
        anchor.setRow2(rowIndex);
        anchor.setDx2((4 + thumbnail.width()) * Units.EMU_PER_PIXEL);
        anchor.setDy2((4 + thumbnail.height()) * Units.EMU_PER_PIXEL);
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
        drawing.createPicture(anchor, pictureIdx);
    }

    private record Thumbnail(byte[] data, int width, int height) {}

    private Thumbnail thumbnail(byte[] original) {
        if (original == null) {
            return null;
        }
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

    private String gender(String name) {
        return switch (name) {
            case "MAN" -> "수컷";
            case "WOMAN" -> "암컷";
            default -> "미상";
        };
    }
}
