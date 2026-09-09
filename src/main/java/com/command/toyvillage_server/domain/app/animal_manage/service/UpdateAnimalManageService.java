package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalManageRequest;
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

    @Transactional
    public void execute(Long animalManageId, AnimalManageRequest request) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        animalManage.update(
            animalKindRepository.findById(request.animalKindId())
                .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION),
            request.animalName(),
            request.animalGender(),
            request.birthYear(),
            request.otherInfo(),
            fileRepository.findByFileKey(request.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION)
        );
    }
}
