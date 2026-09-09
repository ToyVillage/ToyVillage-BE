package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FeedLogRepository extends JpaRepository<FeedLog, Long> {

    List<FeedLog> findAllByFeedDate(LocalDate feedDate);

    List<FeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);

    List<FeedLog> findAllByAnimalManage_IdAndAppAdmin_IdOrderByIdDesc(Long animalManageId, Long writerId);
}
