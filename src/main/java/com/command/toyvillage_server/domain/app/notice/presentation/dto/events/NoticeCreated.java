package com.command.toyvillage_server.domain.app.notice.presentation.dto.events;

import java.util.List;

public record NoticeCreated(Long noticeId, String title, List<Long> teamIds) {
    public static NoticeCreated of(
        Long noticeId, String title, List<Long> teamIds
    ) {
        return new NoticeCreated(noticeId, title, teamIds);
    }
}
