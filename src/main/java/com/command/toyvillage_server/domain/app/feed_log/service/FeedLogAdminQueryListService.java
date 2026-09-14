package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminQueryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FeedLogAdminQueryListService {
    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public FeedLogAdminQueryListResponse execute(LocalDate date) {
        return FeedLogAdminQueryListResponse.from(
                feedLogRepository.findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThan(
                        date.atStartOfDay(), date.plusDays(1).atStartOfDay())
        );
    }
}
