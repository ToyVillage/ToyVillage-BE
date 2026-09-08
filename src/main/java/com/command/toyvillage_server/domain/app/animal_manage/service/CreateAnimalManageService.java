package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalManageRequest;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAnimalManageService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalKindRepository animalKindRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(AnimalManageRequest request) {
        AnimalManage animalManage = AnimalManage.builder()
            .animalKind(animalKindRepository.findById(request.animalKindId())
                .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION))
            .animalName(request.animalName())
            .animalGender(request.animalGender())
            .birthYear(request.birthYear())
            .otherInfo(request.otherInfo())
            .animalImage(fileRepository.findByFileKey(request.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION))
            .build();

        animalManageRepository.save(animalManage);
    }
}
