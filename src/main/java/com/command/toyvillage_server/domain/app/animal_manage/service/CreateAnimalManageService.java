package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.CreateAnimalManageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAnimalManageService {
    private final AnimalManageRepository animalManageRepository;

    @Transactional
    public void execute(CreateAnimalManageRequest request) {
        AnimalManage animalManage = AnimalManage.builder()
            .animalName(request.animalName())
            .animalGender(request.animalGender())
            .birthYear(request.birthYear())
            .otherInfo(request.otherInfo())
            .animalImage(request.animalImage())
            .build();

        animalManageRepository.save(animalManage);
    }
}
