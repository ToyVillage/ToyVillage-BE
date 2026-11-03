package com.command.toyvillage_server.domain.app.task.presentation.dto.response;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import lombok.Builder;

import java.util.List;

@Builder
public record AssigneeResponse(
        Long id,
        String name,
        String position
) {
    public static List<AssigneeResponse> listOf(List<AppAdmin> assignees) {
        return assignees.stream()
                .map(AssigneeResponse::from)
                .toList();
    }

    private static AssigneeResponse from(AppAdmin assignee) {
        return AssigneeResponse.builder()
                .id(assignee.getId())
                .name(assignee.getName())
                .position(assignee.getPosition())
                .build();
    }
}
