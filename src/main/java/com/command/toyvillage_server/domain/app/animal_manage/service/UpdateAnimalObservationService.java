package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservationFile;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalObservationNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalObservationRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateAnimalObservationService {
    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long animalManageId, Long observationId, AnimalObservationRequest request) {
        AnimalObservation observation = animalObservationRepository
            .findByIdAndAnimalManageId(observationId, animalManageId)
            .orElseThrow(() -> AnimalObservationNotFoundException.EXCEPTION);

        List<File> files = List.of();

        if (request.fileKeys() != null && !request.fileKeys().isEmpty()) {
            files = fileRepository.findAllByFileKeyIn(request.fileKeys());

            if (files.size() != request.fileKeys().size()) {
                throw FileNotFoundException.EXCEPTION;
            }
        }

        observation.update(
            request.title(),
            request.content()
        );

        animalObservationFileRepository.deleteAllByAnimalObservationId(observationId);
        List<AnimalObservationFile> observationFiles = files.stream()
            .map(file -> AnimalObservationFile.builder()
                .animalObservation(observation)
                .file(file)
                .build())
            .toList();
        animalObservationFileRepository.saveAll(observationFiles);
    }
}
