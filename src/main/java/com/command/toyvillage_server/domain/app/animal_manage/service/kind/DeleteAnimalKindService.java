package com.command.toyvillage_server.domain.app.animal_manage.service.kind;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalKind;
import com.command.toyvillage_server.domain.app.animal_manage.domain.repository.AnimalKindRepository;
import com.command.toyvillage_server.domain.app.animal_manage.exception.AnimalKindNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAnimalKindService {
    private final AnimalKindRepository animalKindRepository;

    @Transactional
    public void execute(Long animalKindId) {
        AnimalKind animalKind = animalKindRepository.findById(animalKindId)
            .orElseThrow(() -> AnimalKindNotFoundException.EXCEPTION);

        animalKindRepository.delete(animalKind);
    }
}
