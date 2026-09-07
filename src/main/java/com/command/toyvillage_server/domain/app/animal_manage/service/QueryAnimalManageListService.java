package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalManageQueryListObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryAnimalManageListService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalKindRepository animalKindRepository;

    @Transactional(readOnly = true)
    public Page<AnimalManageQueryListObjectResponse> execute(Long animalKindId, Pageable pageable) {
        if (!animalKindRepository.existsById(animalKindId)) {
            throw AnimalKindNotFoundException.EXCEPTION;
        }

        return animalManageRepository.findAllByAnimalKindId(animalKindId, pageable)
            .map(AnimalManageQueryListObjectResponse::from);
    }
}
