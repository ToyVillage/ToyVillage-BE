package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FifthFloorFeedLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogUpdateService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long id, FifthFloorFeedLogRequest request) {
        FifthFloorFeedLog feedLog = fifthFloorFeedLogRepository.findByIdAndAppAdmin_Id(id, userFacade.getCurrentUserId())
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);

        feedLog.update(
                request.feedDate(),
                request.feedType(),
                request.feedAmount(),
                request.feedUnit()
        );
    }
}
