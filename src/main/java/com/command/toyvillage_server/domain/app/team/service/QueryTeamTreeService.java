package com.command.toyvillage_server.domain.app.team.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdminRole;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import com.command.toyvillage_server.domain.app.team.domain.Team;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.presentation.dto.response.TeamTreeResponse;
import com.command.toyvillage_server.domain.app.team.presentation.dto.response.TeamTreeResponse.TeamNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryTeamTreeService {
    private final TeamRepository teamRepository;
    private final AppAdminRepository appAdminRepository;
    private final JoinTeamRepository joinTeamRepository;

    @Transactional(readOnly = true)
    public TeamTreeResponse execute() {
        List<AppAdmin> employees = appAdminRepository.findAllByRoleOrderByIdAsc(AppAdminRole.EMPLOYEE);
        List<JoinTeam> joinTeams = joinTeamRepository.findAllWithTeamAndAppAdmin();

        Map<Long, Set<Long>> appAdminIdsByTeamId = joinTeams.stream()
                .collect(Collectors.groupingBy(
                        joinTeam -> joinTeam.getTeam().getId(),
                        Collectors.mapping(joinTeam -> joinTeam.getAppAdmin().getId(), Collectors.toSet())
                ));

        Set<Long> assignedAppAdminIds = joinTeams.stream()
                .map(joinTeam -> joinTeam.getAppAdmin().getId())
                .collect(Collectors.toSet());

        List<TeamNode> teams = teamRepository.findAllByOrderByIdAsc().stream()
                .map(team -> TeamNode.of(team, filterByIds(employees, appAdminIdsByTeamId.getOrDefault(team.getId(), Set.of()))))
                .toList();

        List<AppAdmin> unassigned = employees.stream()
                .filter(employee -> !assignedAppAdminIds.contains(employee.getId()))
                .toList();

        return TeamTreeResponse.of(employees.size(), teams, TeamNode.unassigned(unassigned));
    }

    private List<AppAdmin> filterByIds(List<AppAdmin> employees, Set<Long> ids) {
        return employees.stream()
                .filter(employee -> ids.contains(employee.getId()))
                .toList();
    }
}
