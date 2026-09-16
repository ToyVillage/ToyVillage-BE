package com.command.toyvillage_server.domain.app.animal_manage.service.kind;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalDesignation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalLegalStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAnimalKindService {
    private final AnimalKindRepository animalKindRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final AnimalLegalStatusRepository animalLegalStatusRepository;
    private final AnimalManageRepository animalManageRepository;

    @Transactional(readOnly = true)
    public AnimalKindQueryResponse execute(Long animalKindId) {
        AnimalKind animalKind = animalKindRepository.findById(animalKindId)
            .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION);

        List<AnimalLegalStatus> registeredLegalStatuses = animalLegalStatusRepository.findAll();
        List<AnimalLegalStatusResponse> legalStatuses = animalLegalDesignationRepository.findAllByAnimalKind(animalKind).stream()
            .map(AnimalLegalDesignation::getAnimalLegalStatus)
            .map(kind -> registeredLegalStatuses.stream()
                .filter(legalStatus -> legalStatus.getKind().equals(kind))
                .findFirst()
                .map(AnimalLegalStatusResponse::from)
                .orElse(new AnimalLegalStatusResponse(null, kind)))
            .toList();

        return AnimalKindQueryResponse.of(
            animalKind,
            legalStatuses,
            animalManageRepository.countByAnimalKindId(animalKindId)
        );
    }
}
