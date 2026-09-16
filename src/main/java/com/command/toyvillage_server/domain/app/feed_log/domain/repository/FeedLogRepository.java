package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.animal_manage.domain.enums.AnimalTaxonomic;
import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FeedLogRepository extends JpaRepository<FeedLog, Long> {

    Optional<FeedLog> findByIdAndAppAdmin_IdOrderByIdDesc(Long id, Long writerId);

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    Page<FeedLog> findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThan(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"appAdmin", "animalManage.animalKind"})
    Page<FeedLog> findAllByFeedDateTimeGreaterThanEqualAndFeedDateTimeLessThanAndAnimalManage_AnimalKind_AnimalTaxonomic(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            AnimalTaxonomic animalTaxonomic,
            Pageable pageable
    );

    @EntityGraph(attributePaths = "appAdmin")
    List<FeedLog> findAllByAnimalManage_IdOrderByFeedDateTimeDescIdDesc(Long animalManageId);

    List<FeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);

    List<FeedLog> findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(Long animalManageId, Long writerId);
}
