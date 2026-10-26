package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FifthFloorFeedLogRepository extends JpaRepository<FifthFloorFeedLog, Long> {

    Optional<FifthFloorFeedLog> findByIdAndAppAdmin_Id(Long id, Long writerId);

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    Page<FifthFloorFeedLog> findAllByFeedDate(LocalDate feedDate, Pageable pageable);

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    Page<FifthFloorFeedLog> findAllByFeedDateAndAnimalManage_AnimalKind_AnimalTaxonomic(
            LocalDate feedDate,
            AnimalTaxonomic animalTaxonomic,
            Pageable pageable
    );

    @EntityGraph(attributePaths = "appAdmin")
    List<FifthFloorFeedLog> findAllByAnimalManage_IdOrderByFeedDateDescIdDesc(Long animalManageId);

    List<FifthFloorFeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);

    List<FifthFloorFeedLog> findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(Long animalManageId, Long writerId);
}
