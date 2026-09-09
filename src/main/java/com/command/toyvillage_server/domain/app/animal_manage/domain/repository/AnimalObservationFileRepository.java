package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservationFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalObservationFileRepository extends JpaRepository<AnimalObservationFile, Long> {
    List<AnimalObservationFile> findAllByAnimalObservationId(Long animalObservationId);

    List<AnimalObservationFile> findAllByAnimalObservationIdIn(List<Long> animalObservationIds);

    void deleteAllByAnimalObservationId(Long animalObservationId);
}
