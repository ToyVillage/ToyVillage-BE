package com.command.toyvillage_server.domain.app.notice.presentation.dto.response;

import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;

public record NoticeTeamResponse(Long id, String name) {
    public static NoticeTeamResponse from(NoticeTeam noticeTeam) {
        return new NoticeTeamResponse(noticeTeam.getTeam().getId(), noticeTeam.getTeam().getName());
    }
}
