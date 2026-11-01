package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FifthFloorFeedLogRepository extends JpaRepository<FifthFloorFeedLog, Long> {

    Optional<FifthFloorFeedLog> findByIdAndAppAdmin_Id(Long id, Long writerId);

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    Page<FifthFloorFeedLog> findAllByFeedDateGreaterThanEqualAndFeedDateLessThan(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    Page<FifthFloorFeedLog> findAllByFeedDateGreaterThanEqualAndFeedDateLessThanAndAnimalManage_AnimalKind_AnimalTaxonomic(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            AnimalTaxonomic animalTaxonomic,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    List<FifthFloorFeedLog> findAllByFeedDateGreaterThanEqualAndFeedDateLessThanOrderByFeedDateAscIdAsc(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );

    @EntityGraph(attributePaths = {"appAdmin", "animalManage"})
    List<FifthFloorFeedLog> findByFeedDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @EntityGraph(attributePaths = "appAdmin")
    List<FifthFloorFeedLog> findAllByAnimalManage_IdOrderByFeedDateDescIdDesc(Long animalManageId);

    List<FifthFloorFeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);

    List<FifthFloorFeedLog> findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(Long animalManageId, Long writerId);
}
