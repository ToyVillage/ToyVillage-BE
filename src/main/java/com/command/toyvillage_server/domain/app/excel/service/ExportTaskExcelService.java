package com.command.toyvillage_server.domain.app.excel.service;

import com.command.toyvillage_server.domain.app.excel.service.dto.ExcelSheet;
import com.command.toyvillage_server.global.error.exception.ErrorCode;
import com.command.toyvillage_server.global.error.exception.ToyVillageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportTaskExcelService {

    private static final int PAGE_SIZE = 200;
    private static final int MAX_ROWS = 10_000;

    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;

    public ExcelSheet execute(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        List<List<Object>> rows = new ArrayList<>();
        for (int page = 0; ; page++) {
            List<Task> tasks = taskRepository.findByCreatedAtBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59, 999_999_000),
                PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt", "id")));
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
                        .map(w -> reportStatus(w.getStatus()))
                        .orElse("미제출");
                    rows.add(Arrays.asList(t.getCreatedAt().toLocalDate(), t.getTitle(), t.getContent(),
                        priority(t.getPriority().name()), t.getFinishDate(), taskStatus, assignee.getName(), reportStatus));
                }
            }
            if (rows.size() > MAX_ROWS) {
                throw new ToyVillageException(ErrorCode.EXCEL_EXPORT_TOO_LARGE);
            }
            if (tasks.size() < PAGE_SIZE) {
                return new ExcelSheet(
                    List.of("생성일", "제목", "내용", "우선순위", "마감일", "진행상태", "담당자", "보고상태"), rows);
            }
        }
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

    private String reportStatus(Status value) {
        return switch (value) {
            case PENDING -> "대기";
            case APPROVED -> "승인";
            case REJECTED -> "반려";
            case MISSING -> "미제출";
            case RESUBMIT -> "재제출";
        };
    }
}
