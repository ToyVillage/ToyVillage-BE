package com.command.toyvillage_server.domain.web.event.service;

import com.command.toyvillage_server.domain.web.event.domain.Event;
import com.command.toyvillage_server.domain.web.event.domain.repository.EventRepository;
import com.command.toyvillage_server.domain.web.event.exception.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventDeleteService {
    private final EventRepository eventRepository;

    @Transactional
    public void execute(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
        log.info("이벤트 삭제: {}", event.getId());
        eventRepository.delete(event);
    }
}
