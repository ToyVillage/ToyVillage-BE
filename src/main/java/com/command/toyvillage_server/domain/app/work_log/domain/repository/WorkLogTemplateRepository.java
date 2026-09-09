package com.command.toyvillage_server.domain.app.work_log.domain.repository;

import com.command.toyvillage_server.domain.app.work_log.domain.WorkLogTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkLogTemplateRepository extends JpaRepository<WorkLogTemplate, Long> {
    boolean existsByTemplateTitle(String templateTitle);

    Optional<WorkLogTemplate> findByIdAndDeleteYnFalse(Long workLogTemplateId);

    Page<WorkLogTemplate> findAllByDeleteYnFalse(Pageable pageable);

    Page<WorkLogTemplate> findAllByDeleteYnFalseAndCreatedAt(Pageable pageable, LocalDate createdAt);
}
