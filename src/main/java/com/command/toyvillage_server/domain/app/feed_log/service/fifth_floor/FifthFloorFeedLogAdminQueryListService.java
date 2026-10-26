package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogAdminQueryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogAdminQueryListService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;

    @Transactional(readOnly = true)
    public FifthFloorFeedLogAdminQueryListResponse execute(
            LocalDate date,
            AnimalTaxonomic animalTaxonomic,
            Pageable pageable
    ) {
        Page<FifthFloorFeedLog> feedLogs = animalTaxonomic == null
                ? fifthFloorFeedLogRepository.findAllByFeedDate(date, pageable)
                : fifthFloorFeedLogRepository.findAllByFeedDateAndAnimalManage_AnimalKind_AnimalTaxonomic(
                        date,
                        animalTaxonomic,
                        pageable
                );

        return FifthFloorFeedLogAdminQueryListResponse.from(feedLogs);
    }
}
