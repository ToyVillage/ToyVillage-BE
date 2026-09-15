package com.command.toyvillage_server.domain.app.team.service;

import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.presentation.dto.response.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryTeamListService {
    private final TeamRepository teamRepository;
    private final JoinTeamRepository joinTeamRepository;

    @Transactional(readOnly = true)
    public List<TeamResponse> execute() {
        Map<Long, Long> memberCountsByTeamId = joinTeamRepository.countMembersByTeam().stream()
                .collect(Collectors.toMap(
                        JoinTeamRepository.TeamMemberCount::getTeamId,
                        JoinTeamRepository.TeamMemberCount::getMemberCount
                ));

        return teamRepository.findAllByOrderByIdAsc()
                .stream()
                .map(team -> TeamResponse.from(team, memberCountsByTeamId.getOrDefault(team.getId(), 0L)))
                .toList();
    }
}
