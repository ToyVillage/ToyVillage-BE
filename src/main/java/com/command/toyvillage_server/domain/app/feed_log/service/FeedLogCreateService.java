package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedLogCreateService {
    private final FeedLogRepository feedLogRepository;

    @Transactional
    public void execute(FeedLogRequest feedLogRequest) {
        FeedLog feedLog = FeedLog.builder()
                .feedDate(feedLogRequest.feedDate())
                .feedStartTime(feedLogRequest.feedStartTime())
                .feedEndTime(feedLogRequest.feedEndTime())
                .feedType(feedLogRequest.feedType())
                .feed_amount(feedLogRequest.feed_amount())
                .significant(feedLogRequest.significant())
                .build();

        feedLogRepository.save(feedLog);
    }
}
