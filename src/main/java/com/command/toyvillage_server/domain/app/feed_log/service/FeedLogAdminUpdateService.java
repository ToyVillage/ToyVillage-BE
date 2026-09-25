package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogForbiddenException;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedLogAdminUpdateService {
    private final FeedLogRepository feedLogRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long id, FeedLogRequest feedLogRequest) {
        if (!userFacade.isCurrentUserAppAdmin()) {
            throw FeedLogForbiddenException.EXCEPTION;
        }

        FeedLog feedLog = feedLogRepository.findById(id)
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);

        feedLog.update(
                feedLogRequest.feedDateTime(),
                feedLogRequest.feedType(),
                feedLogRequest.feedAmount(),
                feedLogRequest.significant()
        );
    }
}
