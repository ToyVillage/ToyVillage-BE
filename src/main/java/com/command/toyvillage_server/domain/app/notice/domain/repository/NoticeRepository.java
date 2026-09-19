package com.command.toyvillage_server.domain.app.notice.domain.repository;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("""
        select notice from Notice notice
        where exists (
            select noticeTeam.id from NoticeTeam noticeTeam
            where noticeTeam.notice = notice and noticeTeam.team.id = :teamId
        )
        """)
    Page<Notice> findAllByTeamId(@Param("teamId") Long teamId, Pageable pageable);
}
