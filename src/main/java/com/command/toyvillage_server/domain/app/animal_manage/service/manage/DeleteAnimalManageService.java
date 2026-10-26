package com.command.toyvillage_server.domain.app.animal_manage.service.manage;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalManageService {
    private final AnimalManageRepository animalManageRepository;

    @Transactional
    public void execute(Long animalManageId) {
        AnimalManage animalManage = animalManageRepository.findByIdAndDeleteYnFalse(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        // 급여 기록과 관찰기록은 남겨야 하므로 동물은 삭제 표시만 한다.
        animalManage.changeDeleteYn();
    }
}
