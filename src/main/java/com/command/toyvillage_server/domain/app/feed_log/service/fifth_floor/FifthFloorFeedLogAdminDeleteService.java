package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogForbiddenException;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogAdminDeleteService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long id) {
        if (!userFacade.isCurrentUserAppAdmin()) {
            throw FeedLogForbiddenException.EXCEPTION;
        }

        FifthFloorFeedLog feedLog = fifthFloorFeedLogRepository.findById(id)
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);
        fifthFloorFeedLogRepository.delete(feedLog);
    }
}
