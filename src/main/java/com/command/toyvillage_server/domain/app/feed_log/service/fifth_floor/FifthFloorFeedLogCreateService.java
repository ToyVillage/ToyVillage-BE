package com.command.toyvillage_server.domain.app.feed_log.service.fifth_floor;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.presentation.dto.request.FifthFloorFeedLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FifthFloorFeedLogCreateService {
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;
    private final AnimalManageRepository animalManageRepository;
    private final AppAdminRepository appAdminRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(Long animalManageId, FifthFloorFeedLogRequest request) {
        AppAdmin writer = appAdminRepository.findByIdAndDeleteStatusFalse(userFacade.getCurrentUserId())
                .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        AnimalManage animalManage = animalManageRepository.findByIdAndDeleteYnFalse(animalManageId)
                .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        FifthFloorFeedLog feedLog = FifthFloorFeedLog.builder()
                .appAdmin(writer)
                .animalManage(animalManage)
                .feedDate(request.feedDate())
                .feedType(request.feedType())
                .feedAmount(request.feedAmount())
                .feedUnit(request.feedUnit())
                .build();

        fifthFloorFeedLogRepository.save(feedLog);
    }
}
