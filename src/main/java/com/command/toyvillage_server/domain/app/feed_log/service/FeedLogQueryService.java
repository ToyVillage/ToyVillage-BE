package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedLogQueryService {
    private final AnimalManageRepository animalManageRepository;
    private final FeedLogRepository feedLogRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public List<FeedLogQueryResponse> execute(Long animalManageId) {
        Long userId = userFacade.getCurrentUserId();

        if (!animalManageRepository.existsById(animalManageId)) {
            throw AnimalManageNotFoundException.EXCEPTION;
        }

        return feedLogRepository.findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(animalManageId, userId)
                .stream()
                .map(FeedLogQueryResponse::from)
                .toList();
    }
}
