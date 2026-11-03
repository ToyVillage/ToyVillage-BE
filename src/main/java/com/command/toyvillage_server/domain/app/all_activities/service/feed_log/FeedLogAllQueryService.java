package com.command.toyvillage_server.domain.app.all_activities.service.feed_log;

import com.command.toyvillage_server.domain.app.all_activities.presentation.dto.response.feed_log.FeedLogAllResponse;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedLogAllQueryService {
    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public List<FeedLogAllResponse> execute(LocalDate date) {
        return feedLogRepository
                .findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThanOrderByFeedDateTimeAscIdAsc(
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay()
                )
                .stream()
                .map(FeedLogAllResponse::from)
                .toList();
    }
}
