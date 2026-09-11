package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservationFile;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.response.AnimalObservationListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryAnimalObservationListService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;

    @Transactional(readOnly = true)
    public Page<AnimalObservationListResponse> execute(Long animalManageId, Pageable pageable) {
        if (!animalManageRepository.existsById(animalManageId)) {
            throw AnimalManageNotFoundException.EXCEPTION;
        }

        Page<AnimalObservation> observations =
            animalObservationRepository.findAllByAnimalManageId(animalManageId, pageable);
        List<Long> observationIds = observations.stream()
            .map(AnimalObservation::getId)
            .toList();
        Map<Long, List<AnimalObservationFile>> filesByObservationId =
            animalObservationFileRepository.findAllByAnimalObservationIdIn(observationIds).stream()
                .collect(Collectors.groupingBy(file -> file.getAnimalObservation().getId()));

        return observations.map(observation -> AnimalObservationListResponse.from(
            observation,
            filesByObservationId.getOrDefault(observation.getId(), List.of())
        ));
    }
}
