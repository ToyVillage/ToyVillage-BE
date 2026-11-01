package com.command.toyvillage_server.domain.app.workreport.domain.repository;

import com.command.toyvillage_server.domain.app.workreport.domain.Status;
import com.command.toyvillage_server.domain.app.workreport.domain.WorkReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WorkReportRepository extends JpaRepository<WorkReport, Long> {
    @EntityGraph(attributePaths = {"task", "appAdmin"})
    List<WorkReport> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );

    Optional<WorkReport> findByTask_IdAndAppAdmin_Id(Long taskId, Long appAdminId);
    Page<WorkReport> findAllByStatus(Status status, Pageable pageable);

    long countByStatus(Status status);
    Page<WorkReport> findAllByTask_Creator_Id(Long creatorId, Pageable pageable);
    Page<WorkReport> findAllByStatusAndTask_Creator_Id(Status status, Long creatorId, Pageable pageable);
    long countByStatusAndTask_Creator_Id(Status status, Long creatorId);
    List<WorkReport> findAllByTask_Id(Long taskId);
    List<WorkReport> findAllByTask_IdIn(Collection<Long> taskIds);
    boolean existsByTask_IdAndAppAdmin_Id(Long taskId, Long appAdminId);
}
