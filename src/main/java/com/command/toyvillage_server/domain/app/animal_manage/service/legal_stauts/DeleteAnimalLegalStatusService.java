package com.command.toyvillage_server.domain.app.animal_manage.service.legal_stauts;

import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalLegalStatusNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalLegalStatusService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;

    @Transactional
    public void execute(Long animalLegalStatusId) {
        if (!animalLegalStatusRepository.existsById(animalLegalStatusId)) {
            throw AnimalLegalStatusNotFoundException.EXCEPTION;
        }

        animalLegalStatusRepository.deleteById(animalLegalStatusId);
    }
}
