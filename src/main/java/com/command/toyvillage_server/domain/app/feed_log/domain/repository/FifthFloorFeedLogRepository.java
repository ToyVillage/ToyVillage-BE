package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.domain.FifthFloorFeedLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FifthFloorFeedLogRepository extends JpaRepository<FifthFloorFeedLog, Long> {

    Optional<FifthFloorFeedLog> findByIdAndAppAdmin_Id(Long id, Long writerId);

    @EntityGraph(attributePaths = "appAdmin")
    Page<FifthFloorFeedLog> findAllByFeedDate(LocalDate feedDate, Pageable pageable);

    @EntityGraph(attributePaths = "appAdmin")
    Page<FifthFloorFeedLog> findAllByFeedDateAndAnimalTaxonomic(
            LocalDate feedDate,
            AnimalTaxonomic animalTaxonomic,
            Pageable pageable
    );

    @EntityGraph(attributePaths = "appAdmin")
    List<FifthFloorFeedLog> findAllByAnimalManage_IdOrderByFeedDateDescIdDesc(Long animalManageId);

    List<FifthFloorFeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);

    List<FifthFloorFeedLog> findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(Long animalManageId, Long writerId);

    @Modifying
    @Query("""
        update FifthFloorFeedLog f
        set f.animalName = :animalName,
            f.animalKindName = :animalKindName,
            f.animalTaxonomic = :animalTaxonomic
        where f.animalManage.id = :animalManageId
        """)
    void updateAnimalSnapshot(
            @Param("animalManageId") Long animalManageId,
            @Param("animalName") String animalName,
            @Param("animalKindName") String animalKindName,
            @Param("animalTaxonomic") AnimalTaxonomic animalTaxonomic
    );

    @Modifying
    @Query("""
        update FifthFloorFeedLog f
        set f.animalKindName = :animalKindName,
            f.animalTaxonomic = :animalTaxonomic
        where f.animalManage.id in (
            select a.id from AnimalManage a where a.animalKind.id = :animalKindId
        )
        """)
    void updateAnimalKindSnapshot(
            @Param("animalKindId") Long animalKindId,
            @Param("animalKindName") String animalKindName,
            @Param("animalTaxonomic") AnimalTaxonomic animalTaxonomic
    );

    @Modifying
    @Query("""
        update FifthFloorFeedLog f
        set f.animalManage = null,
            f.animalDeleted = true
        where f.animalManage.id = :animalManageId
        """)
    void detachAnimalManage(@Param("animalManageId") Long animalManageId);
}
