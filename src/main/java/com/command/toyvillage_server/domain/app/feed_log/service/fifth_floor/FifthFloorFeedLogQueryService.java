package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FifthFloorFeedLogQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogQueryService {
    private final AnimalManageRepository animalManageRepository;
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public FifthFloorFeedLogQueryResponse execute(Long animalManageId) {
        Long userId = userFacade.getCurrentUserId();

        if (!animalManageRepository.existsById(animalManageId)) {
            throw AnimalManageNotFoundException.EXCEPTION;
        }

        return FifthFloorFeedLogQueryResponse.from(
                fifthFloorFeedLogRepository.findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(animalManageId, userId)
        );
    }
}
