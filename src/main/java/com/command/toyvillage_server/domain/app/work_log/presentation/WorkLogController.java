package com.command.toyvillage_server.domain.app.work_log.presentation;

import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogTemplateRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.request.WorkLogWriteRequest;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogDetailResponse;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogListResponse;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogTemplateQueryListObjectResponse;
import com.command.toyvillage_server.domain.app.work_log.presentation.dto.response.WorkLogTemplateResponse;
import com.command.toyvillage_server.domain.app.work_log.service.template.WorkLogTemplateAdminCreateService;
import com.command.toyvillage_server.domain.app.work_log.service.template.WorkLogTemplateAdminDeleteService;
import com.command.toyvillage_server.domain.app.work_log.service.template.WorkLogTemplateQueryListService;
import com.command.toyvillage_server.domain.app.work_log.service.template.WorkLogTemplateQueryService;
import com.command.toyvillage_server.domain.app.work_log.service.work_log.WorkLogAdminQueryListService;
import com.command.toyvillage_server.domain.app.work_log.service.work_log.WorkLogDeleteService;
import com.command.toyvillage_server.domain.app.work_log.service.work_log.WorkLogEmployeeQueryListService;
import com.command.toyvillage_server.domain.app.work_log.service.work_log.WorkLogEmployeeUpdateService;
import com.command.toyvillage_server.domain.app.work_log.service.work_log.WorkLogEmployeeWriteService;
import com.command.toyvillage_server.domain.app.work_log.service.work_log.WorkLogQueryService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/work-log")
@RequiredArgsConstructor
public class WorkLogController {
    private final WorkLogTemplateAdminCreateService workLogTemplateAdminCreateService;
    private final WorkLogTemplateAdminDeleteService workLogTemplateAdminDeleteService;
    private final WorkLogTemplateQueryListService workLogTemplateQueryListService;
    private final WorkLogTemplateQueryService workLogTemplateQueryService;
    private final WorkLogEmployeeWriteService workLogEmployeeWriteService;
    private final WorkLogEmployeeUpdateService workLogEmployeeUpdateService;
    private final WorkLogEmployeeQueryListService workLogEmployeeQueryListService;
    private final WorkLogAdminQueryListService workLogAdminQueryListService;
    private final WorkLogQueryService workLogQueryService;
    private final WorkLogDeleteService workLogDeleteService;

    @PostMapping("/template")
    public ResponseEntity<MessageResponse> createWorkLogTemplate(
        @RequestBody @Valid WorkLogTemplateRequest request
    ) {
        workLogTemplateAdminCreateService.execute(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MessageResponse.of("업무일지 양식 생성 성공"));
    }

    @GetMapping("/template")
    public Page<WorkLogTemplateQueryListObjectResponse> getWorkLogTemplateList(
        @PageableDefault(
            size = 4,
            sort = "id",
            direction = Sort.Direction.DESC
        ) Pageable pageable,
        @RequestParam(value = "date", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return workLogTemplateQueryListService.execute(pageable, date);
    }

    @GetMapping("/template/{workLogTemplateId}")
    public WorkLogTemplateResponse getWorkLogTemplateDetail(@PathVariable Long workLogTemplateId) {
        return workLogTemplateQueryService.execute(workLogTemplateId);
    }

    @DeleteMapping("/template/{workLogTemplateId}")
    public MessageResponse deleteWorkLogTemplate(@PathVariable Long workLogTemplateId) {
        workLogTemplateAdminDeleteService.execute(workLogTemplateId);

        return MessageResponse.of("업무일지 양식 삭제 성공");
    }

    @PostMapping("/{workLogTemplateId}")
    public ResponseEntity<MessageResponse> writeWorkLog(
        @PathVariable Long workLogTemplateId,
        @RequestBody @Valid WorkLogWriteRequest request
    ) {
        workLogEmployeeWriteService.execute(workLogTemplateId, request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(MessageResponse.of("업무일지 작성 성공"));
    }

    @GetMapping
    public Page<WorkLogListResponse> getWorkLogList(
        @PageableDefault(size = 4) Pageable pageable,
        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return workLogAdminQueryListService.execute(date, pageable);
    }

    @GetMapping("/employee")
    public Page<WorkLogListResponse> getEmployeeWorkLogList(
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return workLogEmployeeQueryListService.execute(pageable);
    }

    @GetMapping("/{workLogId}")
    public WorkLogDetailResponse getWorkLogDetail(@PathVariable Long workLogId) {
        return workLogQueryService.execute(workLogId);
    }

    @PatchMapping("/{workLogId}")
    public MessageResponse updateWorkLog(
        @PathVariable Long workLogId,
        @RequestBody @Valid WorkLogWriteRequest request
    ) {
        workLogEmployeeUpdateService.execute(workLogId, request);

        return MessageResponse.of("업무일지 수정 성공");
    }

    @DeleteMapping("/{workLogId}")
    public MessageResponse deleteWorkLog(@PathVariable Long workLogId) {
        workLogDeleteService.execute(workLogId);

        return MessageResponse.of("업무일지 삭제 성공");
    }
}
