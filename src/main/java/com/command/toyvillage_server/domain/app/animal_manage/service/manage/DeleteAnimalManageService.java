package com.command.toyvillage_server.domain.app.animal_manage.service.manage;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalManageService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;
    private final AnimalObservationRepository animalObservationRepository;
    private final FeedLogRepository feedLogRepository;
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;

    @Transactional
    public void execute(Long animalManageId) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        animalObservationFileRepository.deleteAllByAnimalObservation_AnimalManage_Id(animalManageId);
        animalObservationRepository.deleteAllByAnimalManageId(animalManageId);
        // 급여 기록은 스냅샷으로 남기고 동물 연결만 끊는다.
        feedLogRepository.detachAnimalManage(animalManageId);
        fifthFloorFeedLogRepository.detachAnimalManage(animalManageId);
        animalManageRepository.delete(animalManage);
    }
}
