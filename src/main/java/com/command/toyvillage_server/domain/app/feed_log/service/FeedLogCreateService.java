package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FeedLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedLogCreateService {
    private final FeedLogRepository feedLogRepository;
    private final AnimalManageRepository animalManageRepository;

    @Transactional
    public void execute(Long animalManageId, FeedLogRequest feedLogRequest) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
                .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        FeedLog feedLog = FeedLog.builder()
                .animalManage(animalManage)
                .feedDate(feedLogRequest.feedDate())
                .feedStartTime(feedLogRequest.feedStartTime())
                .feedType(feedLogRequest.feedType())
                .feed_amount(feedLogRequest.feed_amount())
                .significant(feedLogRequest.significant())
                .build();

        feedLogRepository.save(feedLog);
    }
}
