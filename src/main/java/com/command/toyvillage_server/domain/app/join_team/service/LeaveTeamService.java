package com.command.toyvillage_server.domain.app.join_team.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.join_team.presentation.dto.request.TeamRequest;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveTeamService {
    private final JoinTeamRepository joinTeamRepository;
    private final AppAdminRepository appAdminRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void execute(TeamRequest request, Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw TeamNotFoundException.EXCEPTION;
        }

        for (Long appAdminId : request.appAdminIds().stream().distinct().toList()) {
            if (!appAdminRepository.existsByIdAndDeleteStatusFalse(appAdminId)) {
                throw AppAdminNotFoundException.EXCEPTION;
            }

            joinTeamRepository.findByAppAdmin_IdAndTeam_Id(appAdminId, teamId)
                    .ifPresent(joinTeamRepository::delete);
        }
    }
}
