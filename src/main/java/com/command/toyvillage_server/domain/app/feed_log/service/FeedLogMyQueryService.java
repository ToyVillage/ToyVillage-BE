package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogMyQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedLogMyQueryService {
    private final FeedLogRepository feedLogRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<FeedLogMyQueryResponse> execute(){
        Long userId = userFacade.getCurrentUserId();

        return feedLogRepository.findAllByAppAdmin_IdOrderByIdDesc(userId)
                .stream()
                .map(FeedLogMyQueryResponse::from)
                .toList();
    }

}
