package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogAdminDetailsQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogAdminDetailsQueryService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;

    @Transactional(readOnly = true)
    public FifthFloorFeedLogAdminDetailsQueryResponse execute(Long id) {
        FifthFloorFeedLog feedLog = fifthFloorFeedLogRepository.findById(id)
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);

        return FifthFloorFeedLogAdminDetailsQueryResponse.from(feedLog);
    }
}
