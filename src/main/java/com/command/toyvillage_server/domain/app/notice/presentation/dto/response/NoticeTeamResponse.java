package com.command.toyvillage_server.domain.app.notice.presentation.dto.response;

import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;

public record NoticeTeamResponse(Long id, String name) {
    public static NoticeTeamResponse from(NoticeTeam noticeTeam) {
        if (noticeTeam.getTeam() == null) {
            return new NoticeTeamResponse(null, "삭제된 팀입니다.");
        }
        return new NoticeTeamResponse(noticeTeam.getTeam().getId(), noticeTeam.getTeam().getName());
    }
}
