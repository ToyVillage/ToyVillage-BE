package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AnimalObservationRepository extends JpaRepository<AnimalObservation, Long> {
    Page<AnimalObservation> findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Pageable pageable
    );

    Page<AnimalObservation> findAllByAnimalManageId(Long animalManageId, Pageable pageable);

    Optional<AnimalObservation> findByIdAndAnimalManageId(Long id, Long animalManageId);

}
