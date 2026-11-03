    package com.command.toyvillage_server.domain.web.event.service;

    import com.command.toyvillage_server.domain.web.event.domain.repository.EventRepository;
    import com.command.toyvillage_server.domain.web.event.presentation.dto.response.EventResponse;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Slf4j
    @Service
    @RequiredArgsConstructor

    public class EventQueryAllService {
        private final EventRepository eventRepository;

        @Transactional(readOnly = true)
        public Page<EventResponse> execute(Pageable pageable) {
            return eventRepository.findAll(pageable)
                    .map(EventResponse::from);
        }
    }
