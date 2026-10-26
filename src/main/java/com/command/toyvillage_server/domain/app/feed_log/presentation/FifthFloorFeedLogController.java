package com.command.toyvillage_server.domain.app.feed_log.presentation;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FifthFloorFeedLogRequest;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogAdminDetailsQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogAdminQueryListResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogAdminSalaryHistoryListQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogDetailsQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogMyQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor.*;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed-log/fifth-floor")
public class FifthFloorFeedLogController {
    private final FifthFloorFeedLogCreateService fifthFloorFeedLogCreateService;
    private final FifthFloorFeedLogUpdateService fifthFloorFeedLogUpdateService;
    private final FifthFloorFeedLogAdminUpdateService fifthFloorFeedLogAdminUpdateService;
    private final FifthFloorFeedLogMyQueryService fifthFloorFeedLogMyQueryService;
    private final FifthFloorFeedLogDetailsQueryService fifthFloorFeedLogDetailsQueryService;
    private final FifthFloorFeedLogQueryService fifthFloorFeedLogQueryService;
    private final FifthFloorFeedLogAdminQueryListService fifthFloorFeedLogAdminQueryListService;
    private final FifthFloorFeedLogAdminDetailsQueryService fifthFloorFeedLogAdminDetailsQueryService;
    private final FifthFloorFeedLogAdminSalaryHistoryListQueryService fifthFloorFeedLogAdminSalaryHistoryListQueryService;
    private final FifthFloorFeedLogAdminDeleteService fifthFloorFeedLogAdminDeleteService;

    //admin
    @PutMapping("/admin/{feedLogId}")
    public ResponseEntity<MessageResponse> updateAdminFeedLog(
            @PathVariable("feedLogId") Long feedLogId,
            @RequestBody @Valid FifthFloorFeedLogRequest request
    ) {
        fifthFloorFeedLogAdminUpdateService.execute(feedLogId, request);
        return ResponseEntity.ok(MessageResponse.of("급여일지가 수정되었습니다."));
    }

    @GetMapping("/admin")
    public FifthFloorFeedLogAdminQueryListResponse getFeedLogList(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "animalTaxonomic", required = false) AnimalTaxonomic animalTaxonomic,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return fifthFloorFeedLogAdminQueryListService.execute(date, animalTaxonomic, pageable);
    }

    @GetMapping("/admin/{feedLogId}")
    public FifthFloorFeedLogAdminDetailsQueryResponse getAdminFeedLogDetail(
            @PathVariable("feedLogId") Long feedLogId
    ) {
        return fifthFloorFeedLogAdminDetailsQueryService.execute(feedLogId);
    }

    @GetMapping("/admin/history/{animalManageId}")
    public FifthFloorFeedLogAdminSalaryHistoryListQueryResponse getAdminFeedLogHistory(
            @PathVariable("animalManageId") Long animalManageId
    ) {
        return fifthFloorFeedLogAdminSalaryHistoryListQueryService.execute(animalManageId);
    }

    @GetMapping("/animal/{animalManageId}")
    public FifthFloorFeedLogQueryResponse getFeedLogsByAnimal(
            @PathVariable("animalManageId") Long animalManageId
    ) {
        return fifthFloorFeedLogQueryService.execute(animalManageId);
    }

    @DeleteMapping("/admin/{feedLogId}")
    public void deleteFeedLog(@PathVariable("feedLogId") Long feedLogId) {
        fifthFloorFeedLogAdminDeleteService.execute(feedLogId);
    }

    //직원
    @PostMapping("/{animalManageId}")
    public void createFeedLog(
            @PathVariable("animalManageId") Long animalManageId,
            @RequestBody @Valid FifthFloorFeedLogRequest request
    ) {
        fifthFloorFeedLogCreateService.execute(animalManageId, request);
    }

    @PutMapping("/{feedLogId}")
    public ResponseEntity<MessageResponse> updateFeedLog(
            @PathVariable("feedLogId") Long feedLogId,
            @RequestBody @Valid FifthFloorFeedLogRequest request
    ) {
        fifthFloorFeedLogUpdateService.execute(feedLogId, request);
        return ResponseEntity.ok(MessageResponse.of("급여일지가 수정되었습니다."));
    }

    @GetMapping("/me")
    public FifthFloorFeedLogMyQueryResponse getMyFeedLogs() {
        return fifthFloorFeedLogMyQueryService.execute();
    }

    @GetMapping("/{feedLogId}")
    public FifthFloorFeedLogDetailsQueryResponse getFeedLogDetail(
            @PathVariable("feedLogId") Long feedLogId
    ) {
        return fifthFloorFeedLogDetailsQueryService.execute(feedLogId);
    }
}
