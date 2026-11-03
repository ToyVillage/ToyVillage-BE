package com.command.toyvillage_server.domain.web.event.service;

import com.command.toyvillage_server.domain.web.event.domain.Event;
import com.command.toyvillage_server.domain.web.event.domain.repository.EventRepository;
import com.command.toyvillage_server.domain.web.event.exception.EventNotFoundException;
import com.command.toyvillage_server.domain.web.event.presentation.dto.request.EventRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventUpdateService {
    private final EventRepository eventRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long id, EventRequest eventRequest) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
        File file = fileRepository.findByFileKey(eventRequest.getFileKey()).orElseThrow(() -> FileNotFoundException.EXCEPTION);

        event.update(
                eventRequest.getEventName(),
                eventRequest.getEventDescription(),
                eventRequest.getEventStartDate(),
                eventRequest.getEventEndDate(),
                eventRequest.getEventSubjects(),
                file
        );
        log.info("이벤트 수정 /id : {}",event.getId());
    }
}
