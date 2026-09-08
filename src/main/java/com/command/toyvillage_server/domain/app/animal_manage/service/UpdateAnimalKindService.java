package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
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
public class UpdateAnimalKindService {
    private final AnimalKindRepository animalKindRepository;
    private final AnimalLegalStatusRepository animalLegalStatusRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long animalKindId, AnimalKindRequest request) {
        AnimalKind animalKind = animalKindRepository.findById(animalKindId)
            .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION);

        List<AnimalLegalStatus> animalLegalStatuses = findAnimalLegalStatuses(request.animalLegalDesignation());

        animalKind.update(
            request.animalName(),
            request.animalEngName(),
            request.animalScientificName(),
            request.animalTaxonomic(),
            request.animalDetailKind(),
            fileRepository.findByFileKey(request.fileKey())
                .orElseThrow(() -> FileNotFoundException.EXCEPTION)
        );

        animalLegalDesignationRepository.deleteAllByAnimalKind(animalKind);

        List<AnimalLegalDesignation> animalLegalDesignations = animalLegalStatuses.stream()
            .map(animalLegalStatus -> AnimalLegalDesignation.builder()
                .animalKind(animalKind)
                .animalLegalStatus(animalLegalStatus)
                .build())
            .toList();

        animalLegalDesignationRepository.saveAll(animalLegalDesignations);
    }

    private List<AnimalLegalStatus> findAnimalLegalStatuses(List<Long> animalLegalStatusIds) {
        List<AnimalLegalStatus> animalLegalStatuses = animalLegalStatusRepository.findAllById(animalLegalStatusIds);

        if (animalLegalStatuses.size() != new HashSet<>(animalLegalStatusIds).size()) {
            throw AnimalLegalStatusNotFoundException.EXCEPTION;
        }

        return animalLegalStatuses;
    }
}
