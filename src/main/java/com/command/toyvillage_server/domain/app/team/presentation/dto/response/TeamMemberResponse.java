package com.command.toyvillage_server.domain.app.team.presentation.dto.response;

import com.command.toyvillage_server.domain.app.join_team.domain.JoinTeam;

public record TeamMemberResponse(
        Long id,
        String name
) {
    public static TeamMemberResponse from(JoinTeam joinTeam) {
        return new TeamMemberResponse(
                joinTeam.getAppAdmin().getId(),
                joinTeam.getAppAdmin().getName()
        );
    }
}
