package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalObservationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalObservationService {
    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;

    @Transactional
    public void execute(Long animalManageId, Long observationId) {
        AnimalObservation observation = animalObservationRepository
            .findByIdAndAnimalManageId(observationId, animalManageId)
            .orElseThrow(() -> AnimalObservationNotFoundException.EXCEPTION);

        animalObservationFileRepository.deleteAllByAnimalObservationId(observationId);
        animalObservationRepository.delete(observation);
    }
}
