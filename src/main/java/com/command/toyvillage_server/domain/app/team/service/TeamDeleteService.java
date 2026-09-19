package com.command.toyvillage_server.domain.app.team.service;

import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
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
    private final NoticeTeamRepository noticeTeamRepository;

    @Transactional
    public void execute(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> TeamNotFoundException.EXCEPTION);

        List<NoticeTeam> noticeTeams = noticeTeamRepository.findAllByTeam_Id(teamId);
        noticeTeams.forEach(NoticeTeam::clearTeam);
        // 팀 삭제 전에 외래 키를 비우고 공지의 선택 이력은 유지한다.
        noticeTeamRepository.flush();

        joinTeamRepository.deleteAll(joinTeamRepository.findAllByTeam_Id(teamId));

        teamRepository.delete(team);
    }
}
