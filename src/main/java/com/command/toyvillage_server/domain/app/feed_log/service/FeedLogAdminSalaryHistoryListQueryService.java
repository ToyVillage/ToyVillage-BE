package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.response.FeedLogAdminSalaryHistoryListQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedLogAdminSalaryHistoryListQueryService {
    private final AnimalManageRepository animalManageRepository;
    private final FeedLogRepository feedLogRepository;

    @Transactional(readOnly = true)
    public List<FeedLogAdminSalaryHistoryListQueryResponse> execute(Long animalManageId) {
        if (!animalManageRepository.existsById(animalManageId)) {
            throw AnimalManageNotFoundException.EXCEPTION;
        }

        return feedLogRepository.findAllByAnimalManage_IdOrderByFeedDateTimeDescIdDesc(animalManageId)
                .stream()
                .map(FeedLogAdminSalaryHistoryListQueryResponse::from)
                .toList();
    }
}
