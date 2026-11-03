package com.command.toyvillage_server.domain.web.event.domain.repository;

import com.command.toyvillage_server.domain.web.event.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = "file")
    Page<Event> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "file")
    Optional<Event> findById(Long id);
}
