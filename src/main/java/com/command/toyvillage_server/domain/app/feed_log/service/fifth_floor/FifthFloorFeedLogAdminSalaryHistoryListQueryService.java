package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogAdminSalaryHistoryListQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogAdminSalaryHistoryListQueryService {
    private final AnimalManageRepository animalManageRepository;
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;

    @Transactional(readOnly = true)
    public FifthFloorFeedLogAdminSalaryHistoryListQueryResponse execute(Long animalManageId) {
        if (!animalManageRepository.existsById(animalManageId)) {
            throw AnimalManageNotFoundException.EXCEPTION;
        }

        return FifthFloorFeedLogAdminSalaryHistoryListQueryResponse.from(
                fifthFloorFeedLogRepository.findAllByAnimalManage_IdOrderByFeedDateDescIdDesc(animalManageId)
        );
    }
}
