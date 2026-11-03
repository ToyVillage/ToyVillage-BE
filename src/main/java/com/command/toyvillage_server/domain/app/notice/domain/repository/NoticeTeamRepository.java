package com.command.toyvillage_server.domain.app.notice.domain.repository;

import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeTeamRepository extends JpaRepository<NoticeTeam, Long> {
    @EntityGraph(attributePaths = "team")
    List<NoticeTeam> findAllByNotice_IdOrderByIdAsc(Long noticeId);

    @EntityGraph(attributePaths = "team")
    List<NoticeTeam> findAllByNotice_IdInOrderByIdAsc(List<Long> noticeIds);

    @EntityGraph(attributePaths = "notice")
    List<NoticeTeam> findAllByTeam_Id(Long teamId);

    boolean existsByNotice_Id(Long noticeId);

    void deleteAllByNotice_Id(Long noticeId);
}
