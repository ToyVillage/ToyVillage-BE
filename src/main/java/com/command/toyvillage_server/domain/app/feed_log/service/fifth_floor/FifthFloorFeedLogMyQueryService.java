package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogMyQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogMyQueryService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public FifthFloorFeedLogMyQueryResponse execute() {
        Long userId = userFacade.getCurrentUserId();

        return FifthFloorFeedLogMyQueryResponse.from(
                fifthFloorFeedLogRepository.findAllByAppAdmin_IdOrderByIdDesc(userId)
        );
    }
}
