package com.command.toyvillage_server.domain.app.work_log.domain.repository;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDate;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    @EntityGraph(attributePaths = {"template", "appAdmin"})
    List<WorkLog> findByWriteAtBetween(LocalDate start, LocalDate end, Pageable pageable);

    long countByWriteAtGreaterThanEqualAndWriteAtLessThan(
        LocalDate startDate,
        LocalDate endDate
    );

    Page<WorkLog> findByAppAdminId(Long appAdminId, Pageable pageable);

    Page<WorkLog> findByWriteAt(LocalDate writeAt, Pageable pageable);

    Page<WorkLog> findByAppAdminIdAndTemplateIdIn(Long appAdminId, List<Long> templateIds, Pageable pageable);

    Page<WorkLog> findByWriteAtAndTemplateIdIn(LocalDate writeAt, List<Long> templateIds, Pageable pageable);
}
