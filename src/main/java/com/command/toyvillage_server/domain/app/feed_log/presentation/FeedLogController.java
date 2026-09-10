package com.command.toyvillage_server.domain.app.feed_log.presentation;

import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminDetailsQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminQueryListResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogMyQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogDetailsQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogCreateService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogAdminDetailsQueryService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogAdminQueryListService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogMyQueryService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogDetailsQueryService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogQueryService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogUpdateService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedLog")
public class FeedLogController {
    private final FeedLogCreateService feedLogCreateService;
    private final FeedLogUpdateService feedLogUpdateService;
    private final FeedLogMyQueryService feedLogMyQueryService;
    private final FeedLogDetailsQueryService feedLogDetailsQueryService;
    private final FeedLogQueryService feedLogQueryService;
    private final FeedLogAdminQueryListService feedLogAdminQueryListService;
    private final FeedLogAdminDetailsQueryService feedLogAdminDetailsQueryService;

    //admin
    @GetMapping("/admin")
    public List<FeedLogAdminQueryListResponse> getFeedLogList(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return feedLogAdminQueryListService.execute(date);
    }

    @GetMapping("/admin/{feedLogId}")
    public FeedLogAdminDetailsQueryResponse getAdminFeedLogDetail(
            @PathVariable("feedLogId") Long feedLogId
    ) {
        return feedLogAdminDetailsQueryService.execute(feedLogId);
    }

    @GetMapping("/animal/{animalManageId}")
    public List<FeedLogQueryResponse> getFeedLogsByAnimal(
            @PathVariable("animalManageId") Long animalManageId
    ) {
        return feedLogQueryService.execute(animalManageId);
    }

    //직원

    @PostMapping("/{animalManageId}")
    public void createFeedLog(
            @PathVariable("animalManageId") Long animalManageId,
            @RequestBody @Valid FeedLogRequest feedLogRequest
    ) {
        feedLogCreateService.execute(animalManageId, feedLogRequest);
    }

    @PutMapping("/{feedLogId}")
    public ResponseEntity<MessageResponse> updateFeedLog(
            @PathVariable("feedLogId") Long feedLogId,
            @RequestBody @Valid FeedLogRequest request
    ) {
        feedLogUpdateService.execute(feedLogId, request);
        return ResponseEntity.ok(MessageResponse.of("급여일지가 수정되었습니다."));
    }

    @GetMapping("/me")
    public List<FeedLogMyQueryResponse> getFeedLog(){
        return feedLogMyQueryService.execute();
    }

    @GetMapping("/{feedLogId}")
    public FeedLogDetailsQueryResponse getFeedLogDetail(
            @PathVariable("feedLogId") Long feedLogId
    ) {
        return feedLogDetailsQueryService.execute(feedLogId);
    }

}
