package com.command.toyvillage_server.domain.app.all_activities.presentation;

import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log.FeedLogAllResponse;
import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log.FifthFloorFeedLogResponse;
import com.command.toyvillage_server.domain.app.all_activities.service.feed_log.FeedLogAllQueryService;
import com.command.toyvillage_server.domain.app.all_activities.service.feed_log.FifthFloorFeedLogAllQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/all-activities")
public class AllActivitiesController {
    private final FeedLogAllQueryService feedLogAllQueryService;
    private final FifthFloorFeedLogAllQueryService fifthFloorFeedLogAllQueryService;

    @GetMapping("/feed-log/fourth-floor")
    public List<FeedLogAllResponse> getFeedLogs(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return feedLogAllQueryService.execute(date);
    }

    @GetMapping("/feed-log/fifth-floor")
    public List<FifthFloorFeedLogResponse> getFifthFloorFeedLogs(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return fifthFloorFeedLogAllQueryService.execute(date);
    }
}
