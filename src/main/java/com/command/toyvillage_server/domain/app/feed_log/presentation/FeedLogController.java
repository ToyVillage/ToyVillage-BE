package com.command.toyvillage_server.domain.app.feed_log.presentation;

import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogCreateService;
import com.command.toyvillage_server.domain.app.feed_log.service.FeedLogUpdateService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed-log")
public class FeedLogController {
    private final FeedLogCreateService feedLogCreateService;
    private final FeedLogUpdateService feedLogUpdateService;

    @PostMapping("/{animal-manage-id}")
    public void createFeedLog(
            @PathVariable("animal-manage-id") Long animalManageId,
            @RequestBody @Valid FeedLogRequest feedLogRequest
    ) {
        feedLogCreateService.execute(animalManageId, feedLogRequest);
    }

    @PutMapping("/{feed-log-id}")
    public ResponseEntity<MessageResponse> updateFeedLog(
            @PathVariable("feed-log-id") Long feedLogId,
            @RequestBody @Valid FeedLogRequest request
    ) {
        feedLogUpdateService.execute(feedLogId, request);
        return ResponseEntity.ok(MessageResponse.of("급여일지가 수정되었습니다."));
    }
}
