package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservationFile;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationFileRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalObservationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalManageNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.AnimalObservationRequest;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAnimalObservationService {
    private final AnimalObservationRepository animalObservationRepository;
    private final AnimalObservationFileRepository animalObservationFileRepository;
    private final AnimalManageRepository animalManageRepository;
    private final AppAdminRepository appAdminRepository;
    private final UserFacade userFacade;
    private final FileRepository fileRepository;

    @Transactional
    public Long execute(Long animalManageId, AnimalObservationRequest request) {
        AnimalManage animalManage = animalManageRepository.findById(animalManageId)
            .orElseThrow(() -> AnimalManageNotFoundException.EXCEPTION);

        AppAdmin author = appAdminRepository.findById(userFacade.getCurrentUserId())
            .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

        List<File> files = List.of();

        if (request.fileKeys() != null && !request.fileKeys().isEmpty()) {
            files = fileRepository.findAllByFileKeyIn(request.fileKeys());

            if (files.size() != request.fileKeys().size()) {
                throw FileNotFoundException.EXCEPTION;
            }
        }

        AnimalObservation observation = AnimalObservation.builder()
            .animalManage(animalManage)
            .author(author)
            .title(request.title())
            .content(request.content())
            .createdAt(LocalDateTime.now())
            .build();
        animalObservationRepository.save(observation);

        List<AnimalObservationFile> observationFiles = files.stream()
            .map(file -> AnimalObservationFile.builder()
                .animalObservation(observation)
                .file(file)
                .build())
            .toList();
        animalObservationFileRepository.saveAll(observationFiles);

        return observation.getId();
    }
}
