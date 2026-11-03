package com.command.toyvillage_server.domain.web.news.domain.repository;

import com.command.toyvillage_server.domain.web.news.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @EntityGraph(attributePaths = "files")
    Page<News> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "files")
    Optional<News> findById(Long id);
}
