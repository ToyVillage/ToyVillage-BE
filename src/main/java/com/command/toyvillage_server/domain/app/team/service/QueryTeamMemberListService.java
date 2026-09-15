package com.command.toyvillage_server.domain.app.team.service;

import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import com.command.toyvillage_server.domain.app.team.presentation.dto.response.TeamMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryTeamMemberListService {
    private final TeamRepository teamRepository;
    private final JoinTeamRepository joinTeamRepository;

    @Transactional(readOnly = true)
    public List<TeamMemberResponse> execute(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw TeamNotFoundException.EXCEPTION;
        }

        return joinTeamRepository.findMembersByTeamId(teamId).stream()
                .map(TeamMemberResponse::from)
                .toList();
    }
}
