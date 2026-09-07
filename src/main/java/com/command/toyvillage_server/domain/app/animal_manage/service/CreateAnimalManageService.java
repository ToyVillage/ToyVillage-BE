package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.CreateAnimalManageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAnimalManageService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final AnimalManageRepository animalManageRepository;

    @Transactional
    public void execute(CreateAnimalManageRequest request) {
        AnimalManage animalManage = AnimalManage.builder()
            .animalName(request.animalName())
            .animalEngName(request.animalEngName())
            .animalScientificName(request.animalScientificName())
            .animalTaxonomic(request.animalTaxonomic())
            .animalDetailKind(request.animalDetailKind())
            .build();

        animalManageRepository.save(animalManage);

        List<AnimalLegalStatus> animalLegalStatuses = animalLegalStatusRepository.findAllById(request.animalLegalDesignation());

        List<AnimalLegalDesignation> animalLegalDesignations = animalLegalStatuses.stream()
            .map(animalLegalStatus -> AnimalLegalDesignation.builder()
                .animalManage(animalManage)
                .animalLegalStatus(animalLegalStatus)
                .build())
            .toList();

        animalLegalDesignationRepository.saveAll(animalLegalDesignations);
    }
}
