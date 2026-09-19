package com.command.toyvillage_server.domain.app.animal_manage.service.manage;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
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

    @Transactional
    public void execute(Long animalManageId) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        animalObservationFileRepository.deleteAllByAnimalObservation_AnimalManage_Id(animalManageId);
        animalObservationRepository.deleteAllByAnimalManageId(animalManageId);
        feedLogRepository.deleteAllByAnimalManage_Id(animalManageId);
        animalManageRepository.delete(animalManage);
    }
}
