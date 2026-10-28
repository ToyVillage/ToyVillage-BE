package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswer;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportWorkLogExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;

    private final WorkLogRepository workLogRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<WorkLog> workLogs = workLogRepository.findByWriteAtBetween(
                startDate, endDate, PageRequest.of(page, PAGE_SIZE, Sort.by("writeAt", "id")));
            for (WorkLog w : workLogs) {
                for (WorkLogAnswer answer : w.getAnswers()) {
                    rows.add(Arrays.asList(w.getWriteAt(), w.getTemplate().getTemplateTitle(), w.getAppAdmin().getName(),
                        answer.getSection().getSectionName(), answer.getQuestion().getQuestion(), answerText(answer)));
                }
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (workLogs.size() < PAGE_SIZE) {
                return new ExcelSheet(List.of("작성일", "템플릿", "작성자", "섹션", "질문", "답변"), rows);
            }
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
}
