package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportNoticeExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;
    private static final List<String> HEADERS = List.of("작성일시", "제목", "내용", "대상 팀");

    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<Notice> notices = noticeRepository.findByCreatedAtBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59, 999_999_000),
                PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt", "id")));
            List<Long> ids = notices.stream().map(Notice::getId).toList();
            Map<Long, String> teamNames = noticeTeamRepository.findAllByNotice_IdInOrderByIdAsc(ids).stream()
                .collect(Collectors.groupingBy(nt -> nt.getNotice().getId(),
                    Collectors.mapping(nt -> nt.getTeam() == null ? "삭제된 팀" : nt.getTeam().getName(),
                        Collectors.joining(", "))));
            for (Notice n : notices) {
                String teams = teamNames.getOrDefault(n.getId(), "");
                rows.add(Arrays.asList(n.getCreatedAt(), n.getTitle(), n.getContent(),
                    teams.isEmpty() ? "전체" : teams));
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (notices.size() < PAGE_SIZE) {
                return new ExcelSheet(HEADERS, rows);
            }
        }
    }
}
