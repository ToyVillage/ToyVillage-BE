package com.command.toyvillage_server.domain.web.animal.service;

import com.command.toyvillage_server.domain.web.animal.domain.Animal;
import com.command.toyvillage_server.domain.web.animal.domain.repository.AnimalRepository;
import com.command.toyvillage_server.domain.web.animal.exception.AnimalNotFoundException;
import com.command.toyvillage_server.domain.web.animal.presentation.dto.request.AnimalRequest;
import com.command.toyvillage_server.domain.web.animal.presentation.dto.response.AnimalResponse;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateAnimalService {
    private final AnimalRepository animalRepository;
    private final AwsS3Provider awsS3Provider;

    @Transactional
    public AnimalResponse execute(AnimalRequest request, Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> AnimalNotFoundException.EXCEPTION);

        String animalImage = awsS3Provider.upload(request.animalImage());

        animal.update(
                request.animalKind(),
                animalImage,
                request.animalDescription(),
                request.animalType(),
                request.popularAnimal()
        );

        animalRepository.save(animal);

        log.info("동물 소개 수정 / Id : {}", animalId);

        return AnimalResponse.from(animal);
    }
}
