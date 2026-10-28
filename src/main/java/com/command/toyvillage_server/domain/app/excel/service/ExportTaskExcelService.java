package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.excel.presentation.ExcelType;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
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
public class ExportTaskExcelService implements ExcelSheetService {

    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;

    @Override
    public ExcelType type() {
        return ExcelType.TASK;
    }

    @Override
    public void execute(XSSFSheet sheet, LocalDate startDate, LocalDate endDate) {
        header(sheet, "생성일", "제목", "내용", "우선순위", "마감일", "진행상태", "담당자", "보고상태");
        LocalDate today = LocalDate.now();
        forEachPage(page -> taskRepository.findByCreatedAtBetween(
            startTime(startDate), endTime(endDate), page(page, "createdAt")), tasks -> {
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
                    row(sheet, t.getCreatedAt().toLocalDate(), t.getTitle(), t.getContent(),
                        priority(t.getPriority().name()), t.getFinishDate(), taskStatus, assignee.getName(), reportStatus);
                }
            }
        });
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
}
