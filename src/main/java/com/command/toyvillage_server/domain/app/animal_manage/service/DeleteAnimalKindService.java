package com.command.toyvillage_server.domain.app.animal_manage.service;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalManage;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalLegalDesignationRepository;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalManageRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        List<AnimalManage> animalManages = animalManageRepository.findAllByAnimalKind_Id(animalKindId);
        for (AnimalManage animalManage : animalManages) {
            deleteAnimalManageService.execute(animalManage.getId());
        }

        animalLegalDesignationRepository.deleteAllByAnimalKind(animalKind);
        animalKindRepository.delete(animalKind);
    }
}
