package com.command.toyvillage_server.domain.app.animal_manage.service.kind;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import com.command.toyvillage_server.domain.app.animal_manage.service.manage.DeleteAnimalManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalKindService {
    private final AnimalKindRepository animalKindRepository;
    private final AnimalManageRepository animalManageRepository;
    private final AnimalLegalDesignationRepository animalLegalDesignationRepository;
    private final DeleteAnimalManageService deleteAnimalManageService;

    @Transactional
    public void execute(Long animalKindId) {
        AnimalKind animalKind = animalKindRepository.findById(animalKindId)
            .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION);

        animalManageRepository.findAllByAnimalKindId(animalKindId)
            .forEach(animalManage -> deleteAnimalManageService.execute(animalManage.getId()));
        animalLegalDesignationRepository.deleteAllByAnimalKindId(animalKindId);
        animalKindRepository.delete(animalKind);
    }
}
