package com.command.toyvillage_server.domain.web.event.service;

import com.command.toyvillage_server.domain.web.event.domain.Event;
import com.command.toyvillage_server.domain.web.event.domain.repository.EventRepository;
import com.command.toyvillage_server.domain.web.event.exception.EventNotFoundException;
import com.command.toyvillage_server.domain.web.event.presentation.dto.response.EventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventQueryService {
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public EventResponse execute(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
        return EventResponse.from(event);
    }
}
