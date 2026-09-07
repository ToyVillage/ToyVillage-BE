package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryListObjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryAnimalKindListService {
    private final AnimalKindRepository animalKindRepository;
    private final AnimalManageRepository animalManageRepository;

    @Transactional(readOnly = true)
    public Page<AnimalKindQueryListObjectResponse> execute(AnimalTaxonomic animalTaxonomic, Pageable pageable) {
        Page<AnimalKind> animalKinds;

        if (animalTaxonomic == null) {
            animalKinds = animalKindRepository.findAll(pageable);
        } else {
            animalKinds = animalKindRepository.findAllByAnimalTaxonomic(animalTaxonomic, pageable);
        }

        return animalKinds.map(animalKind -> AnimalKindQueryListObjectResponse.of(
            animalKind,
            animalManageRepository.countByAnimalKindId(animalKind.getId())
        ));
    }
}
