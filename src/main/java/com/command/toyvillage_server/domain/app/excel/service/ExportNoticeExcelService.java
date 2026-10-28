package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.command.toyvillage_server.domain.app.excel.service.ExcelSheetSupport.*;

@Service
@RequiredArgsConstructor
public class ExportNoticeExcelService implements ExcelSheetService {

    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;

    @Override
    public ExcelType type() {
        return ExcelType.NOTICE;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "작성일", "제목", "내용", "대상 팀");
        forEachPage(page -> noticeRepository.findByCreatedAtBetween(
            startTime(startDate), endTime(endDate), page(page, "createdAt")), notices -> {
            List<Long> ids = notices.stream().map(Notice::getId).toList();
            Map<Long, String> teamNames = noticeTeamRepository.findAllByNotice_IdInOrderByIdAsc(ids).stream()
                .collect(Collectors.groupingBy(nt -> nt.getNotice().getId(),
                    Collectors.mapping(nt -> nt.getTeam() == null ? "삭제된 팀" : nt.getTeam().getName(),
                        Collectors.joining(", "))));
            for (Notice n : notices) {
                String teams = teamNames.getOrDefault(n.getId(), "");
                row(sheet, n.getCreatedAt().toLocalDate(), n.getTitle(), n.getContent(), teams.isEmpty() ? "전체" : teams);
            }
        });
    }
}
