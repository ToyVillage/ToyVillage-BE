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
import java.util.function.Function;
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

        Map<Long, Long> teamIdByAppAdminId = joinTeamRepository.findAllWithTeamAndAppAdmin().stream()
                .collect(Collectors.toMap(
                        joinTeam -> joinTeam.getAppAdmin().getId(),
                        joinTeam -> joinTeam.getTeam().getId()
                ));

        Map<Long, List<AppAdmin>> employeesByTeamId = employees.stream()
                .filter(employee -> teamIdByAppAdminId.containsKey(employee.getId()))
                .collect(Collectors.groupingBy(
                        employee -> teamIdByAppAdminId.get(employee.getId()),
                        Collectors.toList()
                ));

        List<TeamNode> teams = teamRepository.findAllByOrderByIdAsc().stream()
                .map(team -> TeamNode.of(team, employeesByTeamId.getOrDefault(team.getId(), List.of())))
                .toList();

        List<AppAdmin> unassigned = employees.stream()
                .filter(employee -> !teamIdByAppAdminId.containsKey(employee.getId()))
                .toList();

        return TeamTreeResponse.of(employees.size(), teams, TeamNode.unassigned(unassigned));
    }
}
