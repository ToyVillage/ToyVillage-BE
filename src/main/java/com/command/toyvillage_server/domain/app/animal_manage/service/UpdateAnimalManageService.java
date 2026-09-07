package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalManageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAnimalManageService {
    private final AnimalManageRepository animalManageRepository;

    @Transactional
    public void execute(Long animalManageId, AnimalManageRequest request) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> );
    }
}
