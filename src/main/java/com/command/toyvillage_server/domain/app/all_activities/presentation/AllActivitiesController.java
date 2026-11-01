package com.command.toyvillage_server.domain.app.all_activities.presentation;

import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log.FeedLogAllResponse;
import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log.FifthFloorFeedLogResponse;
import com.command.toyvillage_server.domain.app.all_activities.service.feed_log.FeedLogAllQueryService;
import com.command.toyvillage_server.domain.app.all_activities.service.feed_log.FifthFloorFeedLogAllQueryService;
import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.animal_observation.AnimalObservationAllResponse;
import com.command.toyvillage_server.domain.app.all_activities.service.animal_observation.AnimalObservationAllQueryService;
import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.work_log.WorkLogAllResponse;
import com.command.toyvillage_server.domain.app.all_activities.service.work_log.WorkLogAllQueryService;
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
    private final AnimalObservationAllQueryService animalObservationAllQueryService;
    private final WorkLogAllQueryService workLogAllQueryService;

    @GetMapping("/animal-observation")
    public List<AnimalObservationAllResponse> getAnimalObservations(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return animalObservationAllQueryService.execute(date);
    }

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

    @GetMapping("/work-log")
    public List<WorkLogAllResponse> getWorkLogs(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return workLogAllQueryService.execute(date);
    }
}
