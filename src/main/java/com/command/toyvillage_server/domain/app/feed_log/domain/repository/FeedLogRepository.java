package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedLogRepository extends JpaRepository<FeedLog, Long> {

    List<FeedLog> findAllByAppAdmin_IdOrderByIdDesc(Long writerId);
}
