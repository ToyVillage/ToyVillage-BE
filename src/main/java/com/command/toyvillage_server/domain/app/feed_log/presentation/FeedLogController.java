package com.command.toyvillage_server.domain.app.feed_log.presentation;

import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogMyQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogDetailsQueryResponse;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogCreateService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogMyQueryService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogDetailsQueryService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogUpdateService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedLog")
public class FeedLogController {
    private final FeedLogCreateService feedLogCreateService;
    private final FeedLogUpdateService feedLogUpdateService;
    private final FeedLogMyQueryService feedLogMyQueryService;
    private final FeedLogDetailsQueryService feedLogQueryService;

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
        return feedLogQueryService.execute(feedLogId);
    }
}
