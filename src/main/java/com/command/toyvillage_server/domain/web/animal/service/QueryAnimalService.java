package com.command.toyvillage_server.domain.web.animal.service;

import com.command.toyvillage_server.domain.web.animal.domain.Animal;
import com.command.toyvillage_server.domain.web.animal.domain.repository.AnimalRepository;
import com.command.toyvillage_server.domain.web.animal.exception.AnimalNotFoundException;
import com.command.toyvillage_server.domain.web.animal.presentation.dto.response.AnimalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryAnimalService {
    private final AnimalRepository animalRepository;

    @Transactional(readOnly = true)
    public AnimalResponse execute(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> AnimalNotFoundException.EXCEPTION);

        return AnimalResponse.from(animal);
    }
}
