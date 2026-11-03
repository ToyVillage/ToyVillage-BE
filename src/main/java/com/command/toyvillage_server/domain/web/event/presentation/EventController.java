package com.command.toyvillage_server.domain.web.event.presentation;

import com.command.toyvillage_server.domain.web.event.presentation.dto.request.EventRequest;
import com.command.toyvillage_server.domain.web.event.presentation.dto.response.EventResponse;
import com.command.toyvillage_server.domain.web.event.presentation.dto.response.MessageResponse;
import com.command.toyvillage_server.domain.web.event.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventQueryAllService eventQueryAllService;
    private final EventQueryService eventQueryService;
    private final EventCreateService eventCreateService;
    private final EventDeleteService eventDeleteService;
    private final EventUpdateService eventUpdateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EventResponse> getQueryAllEvents(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return eventQueryAllService.execute(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventResponse getQueryEvents(@PathVariable Long id) {
        return eventQueryService.execute(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponse> createEvent(@RequestBody @Valid EventRequest eventRequest) {
        eventCreateService.execute(eventRequest);
        return ResponseEntity.ok(
                new MessageResponse("이벤트가 생성되었습니다")
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent(@PathVariable Long id) {
        eventDeleteService.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> updateEvent(@PathVariable Long id, @RequestBody @Valid EventRequest eventRequest) {
        eventUpdateService.execute(id, eventRequest);
        return ResponseEntity.ok(
                new MessageResponse("이벤트가 변경되었습니다")
        );
    }
}
