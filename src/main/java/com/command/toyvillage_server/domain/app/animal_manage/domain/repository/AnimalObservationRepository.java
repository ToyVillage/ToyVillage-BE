package com.command.toyvillage_server.domain.app.animal_manage.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.AnimalObservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalObservationRepository extends JpaRepository<AnimalObservation, Long> {
    @EntityGraph(attributePaths = {"author", "animalManage.animalKind"})
    List<AnimalObservation> findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtAscIdAsc(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
    );

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
    );

    Page<AnimalObservation> findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Pageable pageable
    );

    Page<AnimalObservation> findAllByAnimalManageId(Long animalManageId, Pageable pageable);

    Optional<AnimalObservation> findByIdAndAnimalManageId(Long id, Long animalManageId);

}
