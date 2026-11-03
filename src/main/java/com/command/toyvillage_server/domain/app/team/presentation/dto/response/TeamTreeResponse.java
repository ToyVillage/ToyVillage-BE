package com.command.toyvillage_server.domain.app.team.presentation.dto.response;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.team.domain.Team;
import lombok.Builder;

import java.util.List;

@Builder
public record TeamTreeResponse(
        int totalMemberCount,
        List<TeamNode> teams,
        TeamNode unassigned
) {
    public static TeamTreeResponse of(
            int totalMemberCount,
            List<TeamNode> teams,
            TeamNode unassigned
    ) {
        return TeamTreeResponse.builder()
                .totalMemberCount(totalMemberCount)
                .teams(teams)
                .unassigned(unassigned)
                .build();
    }

    @Builder
    public record TeamNode(
            Long id,
            String name,
            int memberCount,
            List<MemberResponse> members
    ) {
        public static TeamNode of(Team team, List<AppAdmin> members) {
            return build(team.getId(), team.getName(), members);
        }

        public static TeamNode unassigned(List<AppAdmin> members) {
            return build(null, "미배정", members);
        }

        private static TeamNode build(Long id, String name, List<AppAdmin> members) {
            return TeamNode.builder()
                    .id(id)
                    .name(name)
                    .memberCount(members.size())
                    .members(members.stream().map(MemberResponse::from).toList())
                    .build();
        }
    }

    @Builder
    public record MemberResponse(
            Long id,
            String name,
            String position
    ) {
        private static MemberResponse from(AppAdmin employee) {
            return MemberResponse.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .position(employee.getPosition())
                    .build();
        }
    }
}
