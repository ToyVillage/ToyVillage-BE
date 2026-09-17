package com.command.toyvillage_server.domain.app.join_team.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.join_team.exception.JoinTeamTargetInvalidException;
import com.command.toyvillage_server.domain.app.join_team.presentation.dto.request.TeamRequest;
import com.command.toyvillage_server.domain.app.team.domain.Team;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinTeamService {
    private final JoinTeamRepository joinTeamRepository;
    private final AppAdminRepository appAdminRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void execute(TeamRequest request, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> TeamNotFoundException.EXCEPTION);

        for (Long appAdminId : request.appAdminIds().stream().distinct().toList()) {
            AppAdmin appAdmin = appAdminRepository.findById(appAdminId)
                    .orElseThrow(() -> AppAdminNotFoundException.EXCEPTION);

            if (appAdmin.isAppAdmin()) {
                throw JoinTeamTargetInvalidException.EXCEPTION;
            }

            if (joinTeamRepository.existsByAppAdmin_IdAndTeam_Id(appAdmin.getId(), teamId)) {
                continue;
            }

            joinTeamRepository.save(JoinTeam.create(appAdmin, team));
        }
    }
}
