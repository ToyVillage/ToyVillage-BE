package com.command.toyvillage_server.domain.app.workreport.presentation;

import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.request.WorkRejectRequest;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.request.WorkReportRequest;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.response.WorkReportListResponse;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.response.WorkReportDetailResponse;
import com.command.toyvillage_server.domain.app.workreport.presentation.dto.response.WorkReportResponse;
import com.command.toyvillage_server.domain.app.workreport.service.WorkApproveService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkRejectService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkReportCreateService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkReportUpdateService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkReportDeleteService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkReportQueryService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkReportAllQueryService;
import com.command.toyvillage_server.domain.app.workreport.service.WorkReportDetailQueryService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/work-report")
public class WorkReportController {

    private final WorkReportCreateService workReportCreateService;
    private final WorkApproveService workApproveService;
    private final WorkRejectService workRejectService;
    private final WorkReportUpdateService workReportUpdateService;
    private final WorkReportDeleteService workReportDeleteService;
    private final WorkReportQueryService workReportQueryService;
    private final WorkReportAllQueryService workReportAllQueryService;
    private final WorkReportDetailQueryService workReportDetailQueryService;

    @PostMapping("/{id}")
    public void createWorkReport(@PathVariable("id") Long taskId, @Valid @RequestBody WorkReportRequest workReportRequest) {
        workReportCreateService.execute(taskId, workReportRequest);
    }

    @PatchMapping("/approve/{id}")
    public MessageResponse approveWork(@PathVariable("id") Long workReportId) {
        workApproveService.execute(workReportId);
        return MessageResponse.of("업무 보고가 승인되었습니다.");
    }
    @PatchMapping("/reject/{id}")
    public MessageResponse rejectWork(@PathVariable("id") Long workReportId, @Valid @RequestBody WorkRejectRequest workRejectRequest) {
        workRejectService.execute(workReportId, workRejectRequest);
        return MessageResponse.of("업무 보고가 반려되었습니다.");
    }

    @PutMapping("/{id}")
    public void updateWorkReport(@PathVariable("id") Long workReportId, @Valid @RequestBody WorkReportRequest workReportRequest) {
        workReportUpdateService.execute(workReportId, workReportRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkReport(@PathVariable("id") Long workReportId) {
        workReportDeleteService.execute(workReportId);
    }

    @GetMapping("/{id}")
    public WorkReportResponse getWorkReport(@PathVariable("id") Long taskId) {
        return workReportQueryService.execute(taskId);
    }

    @GetMapping("/detail/{id}")
    public WorkReportDetailResponse getWorkReportDetail(@PathVariable("id") Long workReportId) {
        return workReportDetailQueryService.execute(workReportId);
    }
    @GetMapping
    public WorkReportListResponse getWorkReportDetails(
            @RequestParam(required = false) Status status,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return workReportAllQueryService.execute(status, pageable);
    }
}
