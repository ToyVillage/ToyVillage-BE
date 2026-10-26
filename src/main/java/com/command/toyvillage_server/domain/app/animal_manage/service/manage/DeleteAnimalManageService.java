package com.command.toyvillage_server.domain.app.animal_manage.service.manage;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalManageService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;
    private final AnimalObservationRepository animalObservationRepository;

    @Transactional
    public void execute(Long animalManageId) {
        AnimalManage animalManage = animalManageRepository.findByIdAndDeleteYnFalse(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        animalObservationFileRepository.deleteAllByAnimalObservation_AnimalManage_Id(animalManageId);
        animalObservationRepository.deleteAllByAnimalManageId(animalManageId);
        // 급여 기록은 남겨야 하므로 동물은 삭제 표시만 한다.
        animalManage.changeDeleteYn();
    }
}
