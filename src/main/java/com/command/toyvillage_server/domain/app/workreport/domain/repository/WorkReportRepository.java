package com.command.toyvillage_server.domain.app.workreport.domain.repository;

import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {
    Optional<WorkReport> findByTask_IdAndAppAdmin_Id(Long taskId, Long appAdminId);
    Page<WorkReport> findAllByStatus(Status status, Pageable pageable);

    long countByStatus(Status status);
    List<WorkReport> findAllByTask_Id(Long taskId);
    List<WorkReport> findAllByTask_IdIn(Collection<Long> taskIds);
    boolean existsByTask_IdAndAppAdmin_Id(Long taskId, Long appAdminId);
}
