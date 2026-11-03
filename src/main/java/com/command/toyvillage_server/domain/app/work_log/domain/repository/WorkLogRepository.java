package com.command.toyvillage_server.domain.app.work_log.domain.repository;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    @EntityGraph(attributePaths = {"template", "appAdmin"})
    List<WorkLog> findByWriteAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @EntityGraph(attributePaths = {"template", "appAdmin", "answers"})
    List<WorkLog> findAllByWriteAtGreaterThanEqualAndWriteAtLessThanOrderByWriteAtAscIdAsc(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
    );

    long countByWriteAtGreaterThanEqualAndWriteAtLessThan(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
    );

    Page<WorkLog> findByAppAdminId(Long appAdminId, Pageable pageable);

    Page<WorkLog> findByWriteAtGreaterThanEqualAndWriteAtLessThan(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Pageable pageable
    );

    Page<WorkLog> findByAppAdminIdAndTemplateIdIn(Long appAdminId, List<Long> templateIds, Pageable pageable);

    Page<WorkLog> findByWriteAtGreaterThanEqualAndWriteAtLessThanAndTemplateIdIn(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        List<Long> templateIds,
        Pageable pageable
    );
}
