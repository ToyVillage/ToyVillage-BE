package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalLegalStatusNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalKindRequest;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAnimalKindService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final AnimalKindRepository animalKindRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(AnimalKindRequest request) {
        List<AnimalLegalStatus> animalLegalStatuses = animalLegalStatusRepository.findAllById(request.animalLegalDesignation());

        AnimalKind animalKind = AnimalKind.builder()
            .kindName(request.animalName())
            .engName(request.animalEngName())
            .scientificName(request.animalScientificName())
            .animalTaxonomic(request.animalTaxonomic())
            .detailKind(request.animalDetailKind())
            .kindImage(fileRepository.findByFileKey(request.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION))
            .build();

        animalKindRepository.save(animalKind);

        List<AnimalLegalDesignation> animalLegalDesignations = animalLegalStatuses.stream()
            .map(animalLegalStatus -> AnimalLegalDesignation.builder()
                .animalKind(animalKind)
                .animalLegalStatus(animalLegalStatus)
                .build())
            .toList();

        animalLegalDesignationRepository.saveAll(animalLegalDesignations);
    }
}
