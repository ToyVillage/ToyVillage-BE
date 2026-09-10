package com.command.toyvillage_server.domain.app.feed_log.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
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
    private final AppAdminRepository appAdminRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long animalManageId, FeedLogRequest feedLogRequest) {
        AppAdmin writer = appAdminRepository.findById(userFacade.getCurrentUserId())
                .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
                .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        FeedLog feedLog = FeedLog.builder()
                .appAdmin(writer)
                .animalManage(animalManage)
                .feedDateTime(feedLogRequest.feedDateTime())
                .feedType(feedLogRequest.feedType())
                .feed_amount(feedLogRequest.feed_amount())
                .significant(feedLogRequest.significant())
                .build();

        feedLogRepository.save(feedLog);
    }
}
