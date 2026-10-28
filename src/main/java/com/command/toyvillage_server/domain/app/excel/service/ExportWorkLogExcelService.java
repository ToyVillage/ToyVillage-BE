package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogAnswer;
import com.command.toyvillage_server.domain.app.work_log.domain.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.command.toyvillage_server.domain.app.excel.service.ExcelSheetSupport.*;

@Service
@RequiredArgsConstructor
public class ExportWorkLogExcelService implements ExcelSheetService {

    private final WorkLogRepository workLogRepository;

    @Override
    public ExcelType type() {
        return ExcelType.WORK_LOG;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "작성일", "템플릿", "작성자", "섹션", "질문", "답변");
        forEachPage(page -> workLogRepository.findByWriteAtBetween(
            start(startDate), end(endDate), page(page, "writeAt")), workLogs -> {
            for (WorkLog w : workLogs) {
                for (WorkLogAnswer answer : w.getAnswers()) {
                    row(sheet, w.getWriteAt(), w.getTemplate().getTemplateTitle(), w.getAppAdmin().getName(),
                        answer.getSection().getSectionName(), answer.getQuestion().getQuestion(), answerText(answer));
                }
            }
        });
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
