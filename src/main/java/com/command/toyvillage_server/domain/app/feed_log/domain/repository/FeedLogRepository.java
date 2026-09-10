package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FeedLogRepository extends JpaRepository<FeedLog, Long> {

    Optional<FeedLog> findByIdAndAppAdmin_Id(Long id, Long writerId);

    List<FeedLog> findAllByFeedDate(LocalDate feedDate);

    @EntityGraph(attributePaths = "appAdmin")
    List<FeedLog> findAllByAnimalManage_IdOrderByFeedDateDescFeedStartTimeDescIdDesc(Long animalManageId);

    List<FeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);

    List<FeedLog> findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(Long animalManageId, Long writerId);
}
