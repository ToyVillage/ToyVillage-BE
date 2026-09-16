package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminQueryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FeedLogAdminQueryListService {
    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public FeedLogAdminQueryListResponse execute(LocalDate date, AnimalTaxonomic animalTaxonomic) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.plusDays(1).atStartOfDay();

        if (animalTaxonomic == null) {
            return FeedLogAdminQueryListResponse.from(
                    feedLogRepository.findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThan(
                            startDateTime,
                            endDateTime
                    )
            );
        }

        return FeedLogAdminQueryListResponse.from(
                feedLogRepository.findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThanAndAnimalManage_AnimalKind_AnimalTaxonomic(
                        startDateTime,
                        endDateTime,
                        animalTaxonomic
                )
        );
    }
}
