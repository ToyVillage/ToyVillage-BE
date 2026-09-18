package com.command.toyvillage_server.domain.app.team.service;

import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.team.domain.Team;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamDeleteService {
    private final TeamRepository teamRepository;
    private final JoinTeamRepository joinTeamRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;

    @Transactional
    public void execute(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> TeamNotFoundException.EXCEPTION);

        List<NoticeTeam> noticeTeams = noticeTeamRepository.findAllByTeam_Id(teamId);
        noticeTeamRepository.deleteAll(noticeTeams);
        // 공지와 팀을 삭제하기 전에 연결 엔티티의 삭제를 먼저 반영한다.
        noticeTeamRepository.flush();

        for (NoticeTeam noticeTeam : noticeTeams) {
            Notice notice = noticeTeam.getNotice();
            if (!noticeTeamRepository.existsByNotice_Id(notice.getId())) {
                noticeRepository.delete(notice);
            }
        }

        joinTeamRepository.deleteAll(joinTeamRepository.findAllByTeam_Id(teamId));

        teamRepository.delete(team);
    }
}
