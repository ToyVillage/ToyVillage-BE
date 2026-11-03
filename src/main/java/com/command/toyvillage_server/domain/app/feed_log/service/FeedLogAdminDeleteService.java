package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogForbiddenException;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedLogAdminDeleteService {
    private final FeedLogRepository feedLogRepository;
    private final UserFacade userFacade;

    public void execute(Long id){
        if (!userFacade.isCurrentUserAppAdmin()) {
            throw FeedLogForbiddenException.EXCEPTION;
        }

        FeedLog feedLog = feedLogRepository.findById(id)
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);
        feedLogRepository.delete(feedLog);
    }
}
