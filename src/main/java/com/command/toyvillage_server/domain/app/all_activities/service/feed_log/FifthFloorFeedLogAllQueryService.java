package com.command.toyvillage_server.domain.app.all_activities.service.feed_log;

import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log.FifthFloorFeedLogResponse;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogAllQueryService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;

    @Transactional(readOnly = true)
    public List<FifthFloorFeedLogResponse> execute(LocalDate date) {
        return fifthFloorFeedLogRepository.findAllByFeedDateOrderByIdAsc(date)
                .stream()
                .map(FifthFloorFeedLogResponse::from)
                .toList();
    }
}
