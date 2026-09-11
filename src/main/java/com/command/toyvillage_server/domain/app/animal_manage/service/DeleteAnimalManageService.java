package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteAnimalManageService {
    private final AnimalManageRepository animalManageRepository;
    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;

    @Transactional
    public void execute(Long animalManageId) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        List<Long> observationIds = animalObservationRepository
            .findAllByAnimalManage_Id(animalManageId)
            .stream()
            .map(AnimalObservation::getId)
            .toList();

        if (!observationIds.isEmpty()) {
            animalObservationFileRepository.deleteAll(
                animalObservationFileRepository.findAllByAnimalObservationIdIn(observationIds)
            );
        }

        animalObservationRepository.deleteAllByAnimalManage_Id(animalManageId);
        animalManageRepository.delete(animalManage);
    }
}
