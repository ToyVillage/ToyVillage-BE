package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.CreateAnimalKindRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAnimalKindService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final AnimalManageRepository animalManageRepository;

    @Transactional
    public void execute(CreateAnimalKindRequest request) {
        AnimalKind animalKind = AnimalKind.builder()
            .kindName(request.animalName())
            .engName(request.animalEngName())
            .scientificName(request.animalScientificName())
            .animalTaxonomic(request.animalTaxonomic())
            .detailKind(request.animalDetailKind())
            .build();

        animalManageRepository.save(animalKind);

        List<AnimalLegalStatus> animalLegalStatuses = animalLegalStatusRepository.findAllById(request.animalLegalDesignation());

        List<AnimalLegalDesignation> animalLegalDesignations = animalLegalStatuses.stream()
            .map(animalLegalStatus -> AnimalLegalDesignation.builder()
                .animalKind(animalKind)
                .animalLegalStatus(animalLegalStatus)
                .build())
            .toList();

        animalLegalDesignationRepository.saveAll(animalLegalDesignations);
    }
}
