package com.command.toyvillage_server.domain.app.team.presentation.dto.response;

import com.command.toyvillage_server.domain.app.team.domain.Team;

public record TeamResponse(
        Long id,
        String name,
        Long teamMemberCount
) {
    public static TeamResponse of(Team team, Long teamMemberCount) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                teamMemberCount
        );
    }
}
