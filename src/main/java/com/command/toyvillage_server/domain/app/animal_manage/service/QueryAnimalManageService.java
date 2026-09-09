package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalManageQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAnimalManageService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;

    @Transactional(readOnly = true)
    public AnimalManageQueryResponse execute(Long animalManageId) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        List<String> legalStatuses = animalLegalDesignationRepository
            .findAllByAnimalKind(animalManage.getAnimalKind()).stream()
            .map(AnimalLegalDesignation::getAnimalLegalStatus)
            .map(animalLegalStatus -> animalLegalStatus.getKind())
            .toList();

        return AnimalManageQueryResponse.of(animalManage, legalStatuses);
    }
}
