package com.command.toyvillage_server.domain.web.animal.service;

import com.command.toyvillage_server.domain.web.animal.domain.Animal;
import com.command.toyvillage_server.domain.web.animal.domain.repository.AnimalRepository;
import com.command.toyvillage_server.domain.web.animal.exception.AnimalNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteAnimalService {
    private final AnimalRepository animalRepository;

    @Transactional
    public void execute(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> AnimalNotFoundException.EXCEPTION);

        animalRepository.delete(animal);

        log.info("동물 소개 삭제 /  id : {}", animalId);
    }
}
