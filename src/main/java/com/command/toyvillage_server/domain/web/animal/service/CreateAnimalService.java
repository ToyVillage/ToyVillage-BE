package com.command.toyvillage_server.domain.web.animal.service;

import com.command.toyvillage_server.domain.web.animal.domain.Animal;
import com.command.toyvillage_server.domain.web.animal.domain.repository.AnimalRepository;
import com.command.toyvillage_server.domain.web.animal.presentation.dto.request.AnimalRequest;
import com.command.toyvillage_server.global.aws.s3.AwsS3Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAnimalService {
    private final AnimalRepository animalRepository;
    private final AwsS3Provider awsS3Provider;

    @Transactional
    public void execute(AnimalRequest request) {
        String animalFile = awsS3Provider.upload(request.animalImage());

        Animal animal = Animal.builder()
                .animalKind(request.animalKind())
                .animalImage(animalFile)
                .animalDescription(request.animalDescription())
                .animalType(request.animalType())
                .popularAnimal(request.popularAnimal())
                .build();

        animalRepository.save(animal);

        log.info("동물 소개 생성 / Id : {}", animal.getId());
    }
}
