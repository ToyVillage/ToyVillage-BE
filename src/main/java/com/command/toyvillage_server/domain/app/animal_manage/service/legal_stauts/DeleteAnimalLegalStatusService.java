package com.command.toyvillage_server.domain.app.animal_manage.service.legal_stauts;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalLegalStatus;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalStatusRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalLegalStatusNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalLegalStatusService {
    private final AnimalLegalStatusRepository animalLegalStatusRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;

    @Transactional
    public void execute(Long animalLegalStatusId) {
        AnimalLegalStatus animalLegalStatus = animalLegalStatusRepository.findById(animalLegalStatusId)
            .orElseThrow(() -> AnimalLegalStatusNotFoundException.EXCEPTION);

        animalLegalDesignationRepository.deleteAllByAnimalLegalStatusId(animalLegalStatusId);
        animalLegalStatusRepository.delete(animalLegalStatus);
    }
}
