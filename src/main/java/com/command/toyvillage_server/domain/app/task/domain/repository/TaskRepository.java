package com.command.toyvillage_server.domain.app.task.domain.repository;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            select t from Task t
            join t.assignees a
            where a.id = :appAdminId
            """)
    Page<Task> findAllAssignedTo(
            @Param("appAdminId") Long appAdminId,
            Pageable pageable
    );

    @Query("""
            select t from Task t
            where size(t.assignees) = (
                select count(w) from WorkReport w
                where w.task = t
                  and w.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
            )
            """)
    Page<Task> findAllCompleted(Pageable pageable);

    @Query("""
            select t from Task t
            where size(t.assignees) <> (
                select count(w) from WorkReport w
                where w.task = t
                  and w.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
            )
            """)
    Page<Task> findAllInProgress(Pageable pageable);

    @Query("""
            select t from Task t
            where size(t.assignees) <> (
                select count(w) from WorkReport w
                where w.task = t
                  and w.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
            )
              and t.finishDate < :today
            """)
    Page<Task> findAllExpired(
            @Param("today") LocalDate today,
            Pageable pageable
    );
}
