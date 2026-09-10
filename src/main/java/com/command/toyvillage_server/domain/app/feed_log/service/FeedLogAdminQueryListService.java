package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminQueryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedLogAdminQueryListService {
    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public List<FeedLogAdminQueryListResponse> execute(LocalDate date) {
        return feedLogRepository.findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThan(
                        date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .stream()
                .map(FeedLogAdminQueryListResponse::from)
                .toList();
    }
}
