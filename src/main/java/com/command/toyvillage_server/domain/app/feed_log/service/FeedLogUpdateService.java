package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedLogUpdateService {
    private final FeedLogRepository feedLogRepository;

    @Transactional
    public void execute(Long id, FeedLogRequest feedLogRequest) {
        FeedLog feedLog = feedLogRepository.findById(id)
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);

        feedLog.update(
                feedLogRequest.feedDate(),
                feedLogRequest.feedStartTime(),
                feedLogRequest.feedEndTime(),
                feedLogRequest.feedType(),
                feedLogRequest.feed_amount(),
                feedLogRequest.significant()
        );
    }
}
