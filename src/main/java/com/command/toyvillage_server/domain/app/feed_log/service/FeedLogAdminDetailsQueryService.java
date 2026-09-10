package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminDetailsQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedLogAdminDetailsQueryService {

    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public FeedLogAdminDetailsQueryResponse execute(Long id) {
        FeedLog feedLog = feedLogRepository.findById(id)
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);

        return FeedLogAdminDetailsQueryResponse.from(feedLog);
    }
}
