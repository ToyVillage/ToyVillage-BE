package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.exception.FeedLogNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogDetailsQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedLogDetailsQueryService {
    private final FeedLogRepository feedLogRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public FeedLogDetailsQueryResponse execute(Long id) {
        FeedLog feedLog = feedLogRepository.findByIdAndAppAdmin_Id(id, userFacade.getCurrentUserId())
                .orElseThrow(() -> FeedLogNotFoundException.EXCEPTION);
        return FeedLogDetailsQueryResponse.from(feedLog);
    }
}
