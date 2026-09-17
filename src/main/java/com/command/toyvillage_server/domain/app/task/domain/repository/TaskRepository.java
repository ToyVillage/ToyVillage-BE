package com.command.toyvillage_server.domain.app.task.domain.repository;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            select count(task)
            from Task task
            where task.createdAt >= :startDateTime
              and task.createdAt < :endDateTime
              and size(task.assignees) = (
                  select count(workReport)
                  from WorkReport workReport
                  where workReport.task = task
                    and workReport.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
                    and workReport.appAdmin member of task.assignees
              )
            """)
    long countCompletedCreatedBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    @Query("""
            select count(task)
            from Task task
            where task.createdAt >= :startDateTime
              and task.createdAt < :endDateTime
              and size(task.assignees) <> (
                  select count(workReport)
                  from WorkReport workReport
                  where workReport.task = task
                    and workReport.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
                    and workReport.appAdmin member of task.assignees
              )
              and task.finishDate >= :today
            """)
    long countInProgressCreatedBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("today") LocalDate today
    );

    @Query("""
            select count(task)
            from Task task
            where task.createdAt >= :startDateTime
              and task.createdAt < :endDateTime
              and size(task.assignees) <> (
                  select count(workReport)
                  from WorkReport workReport
                  where workReport.task = task
                    and workReport.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
                    and workReport.appAdmin member of task.assignees
              )
              and task.finishDate < :today
            """)
    long countExpiredCreatedBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("today") LocalDate today
    );

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
                  and w.appAdmin member of t.assignees
            )
            """)
    Page<Task> findAllCompleted(Pageable pageable);

    @Query("""
            select t from Task t
            where size(t.assignees) <> (
                select count(w) from WorkReport w
                where w.task = t
                  and w.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
                  and w.appAdmin member of t.assignees
            )
              and t.finishDate >= :today
            """)
    Page<Task> findAllInProgress(
            @Param("today") LocalDate today,
            Pageable pageable
    );

    @Query("""
            select t from Task t
            where size(t.assignees) <> (
                select count(w) from WorkReport w
                where w.task = t
                  and w.status = com.command.toyvillage_server.domain.app.workreport.domain.Status.APPROVED
                  and w.appAdmin member of t.assignees
            )
              and t.finishDate < :today
            """)
    Page<Task> findAllExpired(
            @Param("today") LocalDate today,
            Pageable pageable
    );
}
