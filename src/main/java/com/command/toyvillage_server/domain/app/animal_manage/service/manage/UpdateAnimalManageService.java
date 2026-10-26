package com.command.toyvillage_server.domain.app.animal_manage.service.manage;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalManageRequest;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FeedLogRepository;
import com.command.toyvillage_server.domain.app.feed_log.domain.repository.FifthFloorFeedLogRepository;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAnimalManageService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalKindRepository animalKindRepository;
    private final FileRepository fileRepository;
    private final FeedLogRepository feedLogRepository;
    private final FifthFloorFeedLogRepository fifthFloorFeedLogRepository;

    @Transactional
    public void execute(Long animalManageId, AnimalManageRequest request) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        AnimalKind animalKind = animalKindRepository.findById(request.animalKindId())
            .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION);

        animalManage.update(
            animalKind,
            request.animalName(),
            request.animalGender(),
            request.birthYear(),
            request.otherInfo(),
            fileRepository.findByFileKey(request.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION)
        );

        // 급여 기록의 동물 스냅샷도 수정된 정보로 맞춘다.
        feedLogRepository.updateAnimalSnapshot(
            animalManageId, animalManage.getAnimalName(), animalKind.getKindName(), animalKind.getAnimalTaxonomic()
        );
        fifthFloorFeedLogRepository.updateAnimalSnapshot(
            animalManageId, animalManage.getAnimalName(), animalKind.getKindName(), animalKind.getAnimalTaxonomic()
        );
    }
}
