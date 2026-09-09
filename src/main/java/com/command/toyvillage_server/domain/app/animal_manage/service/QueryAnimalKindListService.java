package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryListObjectResponse;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalKindQueryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryAnimalKindListService {
    private final AnimalKindRepository animalKindRepository;
    private final AnimalManageRepository animalManageRepository;

    @Transactional(readOnly = true)
    public AnimalKindQueryListResponse execute(AnimalTaxonomic animalTaxonomic, Pageable pageable) {
        Page<AnimalKind> animalKinds;

        if (animalTaxonomic == null) {
            animalKinds = animalKindRepository.findAll(pageable);
        } else {
            animalKinds = animalKindRepository.findAllByAnimalTaxonomic(animalTaxonomic, pageable);
        }

        Map<Long, Long> animalCounts = animalKinds.isEmpty()
            ? Map.of()
            : animalManageRepository.countByAnimalKindIds(
                animalKinds.stream().map(AnimalKind::getId).toList()
            ).stream().collect(Collectors.toMap(
                count -> (Long) count[0],
                count -> (Long) count[1]
            ));

        Page<AnimalKindQueryListObjectResponse> responses = animalKinds.map(animalKind ->
            AnimalKindQueryListObjectResponse.of(
                animalKind,
                animalCounts.getOrDefault(animalKind.getId(), 0L)
            )
        );

        return AnimalKindQueryListResponse.from(responses);
    }
}
