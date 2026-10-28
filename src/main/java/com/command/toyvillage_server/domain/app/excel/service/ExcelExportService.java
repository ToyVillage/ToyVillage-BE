package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.reservation.domain.Reservation;
import com.command.toyvillage_server.domain.app.reservation.domain.repository.ReservationRepository;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswer;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private static final int THUMBNAIL_SIZE = 120;
    private static final long MAX_IMAGE_PIXELS = 20_000_000;
    private static final int PAGE_SIZE = 200;
    private static final int MAX_DATA_ROWS = 10_000;
    private static final int MAX_ANIMAL_ROWS = 1_000;
    private static final LocalDate MIN_DATE = LocalDate.of(1000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);

    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;
    private final ReservationRepository reservationRepository;
    private final JoinTeamRepository joinTeamRepository;
    private final AnimalManageRepository animalManageRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final WorkLogRepository workLogRepository;
    private final AwsS3Provider awsS3Provider;

    @Transactional(readOnly = true)
    public byte[] execute(ExcelType type, LocalDate startDate, LocalDate endDate) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(type.getTitle());

            switch (type) {
                case NOTICE -> writeNotice(sheet, startDate, endDate);
                case TASK -> writeTask(sheet, startDate, endDate);
                case WORK_REPORT -> writeWorkReport(sheet, startDate, endDate);
                case RESERVATION -> writeReservation(sheet, startDate, endDate);
                case TEAM_MEMBER -> writeTeamMember(sheet);
                case ANIMAL -> writeAnimal(workbook, sheet);
                case WORK_LOG -> writeWorkLog(sheet, startDate, endDate);
            }

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

    private void writeNotice(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "작성일", "제목", "내용", "대상 팀");
        int r = 1;
        for (int page = 0; ; page++) {
            List<Notice> notices = noticeRepository.findByCreatedAtBetween(
                start(startDate), end(endDate), page(page, "createdAt"));
            if (notices.isEmpty()) break;
            List<Long> ids = notices.stream().map(Notice::getId).toList();
            Map<Long, String> teamNames = noticeTeamRepository.findAllByNotice_IdInOrderByIdAsc(ids).stream()
                .collect(Collectors.groupingBy(nt -> nt.getNotice().getId(),
                    Collectors.mapping(nt -> nt.getTeam() == null ? "삭제된 팀" : nt.getTeam().getName(),
                        Collectors.joining(", "))));
            for (Notice n : notices) {
                String teams = teamNames.getOrDefault(n.getId(), "");
                row(sheet, r++, n.getCreatedAt(), n.getTitle(), n.getContent(), teams.isEmpty() ? "전체" : teams);
            }
            if (notices.size() < PAGE_SIZE) break;
        }
    }

    private void writeTask(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "생성일", "제목", "내용", "우선순위", "마감일", "진행상태", "담당자", "보고상태");
        LocalDate today = LocalDate.now();
        int r = 1;
        for (int page = 0; ; page++) {
            List<Task> tasks = taskRepository.findByCreatedAtBetween(
                startTime(startDate), endTime(endDate), page(page, "createdAt"));
            if (tasks.isEmpty()) break;
            Map<Long, List<WorkReport>> reportsByTask = workReportRepository
                .findAllByTask_IdIn(tasks.stream().map(Task::getId).toList()).stream()
                .collect(Collectors.groupingBy(w -> w.getTask().getId()));
            for (Task t : tasks) {
                List<WorkReport> reports = reportsByTask.getOrDefault(t.getId(), List.of());
                long approved = reports.stream()
                    .filter(w -> w.getStatus() == Status.APPROVED && t.isAssignee(w.getAppAdmin().getId()))
                    .count();
                String taskStatus = taskStatus(TaskStatus.of(t.getAssignees().size(), approved, t.getFinishDate(), today));
                for (AppAdmin assignee : t.getAssignees()) {
                    String reportStatus = reports.stream()
                        .filter(w -> w.getAppAdmin().getId().equals(assignee.getId()))
                        .findFirst()
                        .map(w -> reportStatus(w.getStatus().name()))
                        .orElse("미제출");
                    row(sheet, r++, t.getCreatedAt().toLocalDate(), t.getTitle(), t.getContent(),
                        priority(t.getPriority().name()), t.getFinishDate(), taskStatus, assignee.getName(), reportStatus);
                }
            }
            if (tasks.size() < PAGE_SIZE) break;
        }
    }

    private void writeWorkReport(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "작성일", "업무 제목", "작성자", "내용", "비고", "상태", "반려사유");
        int r = 1;
        for (int page = 0; ; page++) {
            List<WorkReport> reports = workReportRepository.findByCreatedAtBetween(
                startTime(startDate), endTime(endDate), page(page, "createdAt"));
            if (reports.isEmpty()) break;
            for (WorkReport w : reports) {
                row(sheet, r++, w.getCreatedAt().toLocalDate(), w.getTask().getTitle(), w.getAppAdmin().getName(),
                    w.getContent(), w.getNote(), reportStatus(w.getStatus().name()), w.getRejectionReason());
            }
            if (reports.size() < PAGE_SIZE) break;
        }
    }

    private void writeReservation(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "예약일", "제목", "예약자명", "인원", "사전답사일", "사전답사 시간", "사전답사 인원",
            "인솔자 수", "방문일", "방문 시간", "퇴장 시간", "상담일", "장소", "금액", "상태");
        int r = 1;
        for (int page = 0; ; page++) {
            List<Reservation> reservations = reservationRepository.findByReservationDateBetween(
                start(startDate), end(endDate), page(page, "reservationDate"));
            if (reservations.isEmpty()) break;
            for (Reservation v : reservations) {
                row(sheet, r++, v.getReservationDate(), v.getTitle(), v.getReservationName(), v.getReservationCount(),
                    v.getVisitSiteDate(), v.getVisitSiteTime() + " ~ " + v.getVisitSiteExitTime(), v.getVisitSiteCount(),
                    v.getLeaderCount(), v.getVisitDate(), v.getVisitTime(), v.getExitTime(), v.getCounselDate(),
                    v.getLocation(), v.getMoney(), v.getStatus().getDescription());
            }
            if (reservations.size() < PAGE_SIZE) break;
        }
    }

    private void writeTeamMember(XSSFSheet sheet) {
        header(sheet, "팀", "이름", "직책", "권한", "등록일");
        int r = 1;
        for (int page = 0; ; page++) {
            List<JoinTeam> joinTeams = joinTeamRepository.findExportPage(PageRequest.of(page, PAGE_SIZE));
            if (joinTeams.isEmpty()) break;
            for (JoinTeam j : joinTeams) {
                AppAdmin a = j.getAppAdmin();
                row(sheet, r++, j.getTeam().getName(), a.getName(), a.getPosition(), role(a.getRole().name()), a.getCreateAt());
            }
            if (joinTeams.size() < PAGE_SIZE) break;
        }
    }

    private void writeAnimal(XSSFWorkbook workbook, XSSFSheet sheet) {
        header(sheet, "사진", "개체명", "종", "영문명", "학명", "분류", "세부종", "성별", "출생년도", "법적지위", "기타정보");
        sheet.setColumnWidth(0, 18 * 256);
        XSSFDrawing drawing = sheet.createDrawingPatriarch();

        int r = 1;
        for (int page = 0; ; page++) {
            List<AnimalManage> animals = animalManageRepository.findExportPage(PageRequest.of(page, PAGE_SIZE));
            if (animals.isEmpty()) break;
            List<Long> kindIds = animals.stream().map(a -> a.getAnimalKind().getId()).distinct().toList();
            Map<Long, String> legalByKind = animalLegalDesignationRepository.findAllByAnimalKind_IdIn(kindIds).stream()
                .collect(Collectors.groupingBy(d -> d.getAnimalKind().getId(),
                    Collectors.mapping(d -> d.getAnimalLegalStatus().getKind(), Collectors.joining(", "))));
            for (AnimalManage a : animals) {
                if (r > MAX_ANIMAL_ROWS) {
                    throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
                }
                AnimalKind kind = a.getAnimalKind();
                Row row = row(sheet, r, null, a.getAnimalName(), kind.getKindName(), kind.getEngName(), kind.getScientificName(),
                    kind.getAnimalTaxonomic().getType(), kind.getDetailKind(), gender(a.getAnimalGender().name()),
                    a.getBirthYear(), legalByKind.getOrDefault(kind.getId(), ""), a.getOtherInfo());
                row.setHeightInPoints(100);

                Thumbnail thumbnail = a.getAnimalImage() == null ? null : thumbnail(awsS3Provider.download(a.getAnimalImage().getFileKey()));
                if (thumbnail == null) {
                    row.getCell(0).setCellValue("이미지 없음");
                } else {
                    int pictureIdx = workbook.addPicture(thumbnail.data(), Workbook.PICTURE_TYPE_PNG);
                    XSSFClientAnchor anchor = new XSSFClientAnchor();
                    anchor.setCol1(0);
                    anchor.setRow1(r);
                    anchor.setDx1(4 * org.apache.poi.util.Units.EMU_PER_PIXEL);
                    anchor.setDy1(4 * org.apache.poi.util.Units.EMU_PER_PIXEL);
                    anchor.setCol2(0);
                    anchor.setRow2(r);
                    anchor.setDx2((4 + thumbnail.width()) * org.apache.poi.util.Units.EMU_PER_PIXEL);
                    anchor.setDy2((4 + thumbnail.height()) * org.apache.poi.util.Units.EMU_PER_PIXEL);
                    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                    drawing.createPicture(anchor, pictureIdx);
                }
                r++;
            }
            if (animals.size() < PAGE_SIZE) break;
        }
    }

    private void writeWorkLog(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "작성일", "템플릿", "작성자", "섹션", "질문", "답변");
        int r = 1;
        for (int page = 0; ; page++) {
            List<WorkLog> workLogs = workLogRepository.findByWriteAtBetween(
                start(startDate), end(endDate), page(page, "writeAt"));
            if (workLogs.isEmpty()) break;
            for (WorkLog w : workLogs) {
                for (WorkLogAnswer answer : w.getAnswers()) {
                    row(sheet, r++, w.getWriteAt(), w.getTemplate().getTemplateTitle(), w.getAppAdmin().getName(),
                        answer.getSection().getSectionName(), answer.getQuestion().getQuestion(), answerText(answer));
                }
            }
            if (workLogs.size() < PAGE_SIZE) break;
        }
    }

    private String answerText(WorkLogAnswer answer) {
        if (!answer.getSelectedOptions().isEmpty()) {
            return answer.getSelectedOptions().stream()
                .map(o -> o.getEtcText() != null ? o.getOption().getContent() + "(" + o.getEtcText() + ")" : o.getOption().getContent())
                .collect(Collectors.joining(", "));
        }
        if (answer.getFile() != null) {
            return "[파일] " + answer.getFile().getFileName();
        }
        return answer.getAnswerText();
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

    private PageRequest page(int number, String dateField) {
        return PageRequest.of(number, PAGE_SIZE, Sort.by(dateField).ascending().and(Sort.by("id").ascending()));
    }

    private LocalDate start(LocalDate value) {
        return value == null ? MIN_DATE : value;
    }

    private LocalDate end(LocalDate value) {
        return value == null ? MAX_DATE : value;
    }

    private LocalDateTime startTime(LocalDate value) {
        return start(value).atStartOfDay();
    }

    private LocalDateTime endTime(LocalDate value) {
        return end(value).atTime(23, 59, 59, 999_999_000);
    }

    private String taskStatus(TaskStatus value) {
        return switch (value) {
            case IN_PROGRESS -> "진행중";
            case COMPLETED -> "완료";
            case EXPIRED -> "기한만료";
        };
    }

    private String priority(String value) {
        return switch (value) {
            case "HIGH" -> "높음";
            case "MEDIUM" -> "보통";
            case "LOW" -> "낮음";
            default -> value;
        };
    }

    private String reportStatus(String value) {
        return switch (value) {
            case "PENDING" -> "대기";
            case "APPROVED" -> "승인";
            case "REJECTED" -> "반려";
            case "MISSING" -> "미제출";
            case "RESUBMIT" -> "재제출";
            default -> value;
        };
    }

    private String role(String value) {
        return switch (value) {
            case "APP_ADMIN" -> "관리자";
            case "EMPLOYEE" -> "직원";
            default -> value;
        };
    }

    private void header(XSSFSheet sheet, String... titles) {
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

    private Row row(XSSFSheet sheet, int index, Object... values) {
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

    private CellStyle dateStyle(XSSFWorkbook workbook) {
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
