package com.command.toyvillage_server.domain.app.feed_log.domain.repository;

import com.command.toyvillage_server.domain.app.feed_log.domain.FeedLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLogRepository extends JpaRepository<FeedLog, Long> {
}
