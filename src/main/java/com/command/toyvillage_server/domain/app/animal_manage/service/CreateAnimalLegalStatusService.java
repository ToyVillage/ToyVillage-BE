package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.presentation.dto.request.CreateAnimalLegalStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAnimalLegalStatusService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;

    @Transactional
    public void execute(CreateAnimalLegalStatusRequest request) {
        AnimalLegalStatus animalLegalStatus = AnimalLegalStatus.builder()
            .kind(request.kind())
            .build();

        animalLegalStatusRepository.save(animalLegalStatus);
    }
}
