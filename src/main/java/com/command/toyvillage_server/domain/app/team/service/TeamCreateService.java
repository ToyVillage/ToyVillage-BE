package com.command.toyvillage_server.domain.app.team.service;

import com.command.toyvillage_server.domain.app.team.domain.Team;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.presentation.dto.request.TeamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamCreateService {
    private final TeamRepository teamRepository;

    @Transactional
    public void execute(TeamRequest request) {
        Team team = Team.builder()
                .name(request.name())
                .build();

        teamRepository.save(team);
    }
}
