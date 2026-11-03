package com.command.toyvillage_server.domain.web.event.service;

import com.command.toyvillage_server.domain.web.event.domain.Event;
import com.command.toyvillage_server.domain.web.event.domain.repository.EventRepository;
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
public class EventCreateService {
    private final EventRepository eventRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(EventRequest eventRequest) {
        File file = fileRepository.findByFileKey(eventRequest.getFileKey()).orElseThrow(() -> FileNotFoundException.EXCEPTION);

        Event event = Event.builder()
                .title(eventRequest.getEventName())
                .description(eventRequest.getEventDescription())
                .startDate(eventRequest.getEventStartDate())
                .endDate(eventRequest.getEventEndDate())
                .subjects(eventRequest.getEventSubjects())
                .file(file)
                .build();

        log.info("이벤트 생성  {}", event.getId());
        eventRepository.save(event);
    }
}
