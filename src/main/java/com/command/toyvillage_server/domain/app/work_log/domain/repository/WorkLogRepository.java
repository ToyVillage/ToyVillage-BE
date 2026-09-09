package com.command.toyvillage_server.domain.app.work_log.domain.repository;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    Page<WorkLog> findByAppAdminId(Long appAdminId, Pageable pageable);

    Page<WorkLog> findByWriteAt(LocalDate writeAt, Pageable pageable);
}
