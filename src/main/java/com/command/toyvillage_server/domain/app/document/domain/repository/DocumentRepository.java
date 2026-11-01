package com.command.toyvillage_server.domain.app.document.domain.repository;

import com.command.toyvillage_server.domain.app.document.domain.Document;
import com.command.toyvillage_server.domain.app.document.domain.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> getAllByTitleContainsIgnoreCase(String title, Pageable pageable);

    Page<Document> getAllByTitleContainsIgnoreCaseAndTypeIn(String title, List<DocumentType> types, Pageable pageable);

    @Query("select d from Document d where d.createdAt between :start and :end or d.createdAt is null")
    List<Document> findExportPage(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);
}
