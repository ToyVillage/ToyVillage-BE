package com.command.toyvillage_server.domain.app.notice.presentation.dto.response;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
public record NoticeListResponseDto(
    List<NoticeResponse> notices,
    int totalPageSize
) {
    public static NoticeListResponseDto from(Page<Notice> notices, Map<Long, List<NoticeTeam>> noticeTeams) {
        return NoticeListResponseDto.builder()
            .notices(notices.map(notice -> NoticeResponse.from(
                notice, noticeTeams.getOrDefault(notice.getId(), List.of())
            )).toList())
            .totalPageSize(notices.getTotalPages())
            .build();
    }

    @Builder
    private record NoticeResponse(
        Long id,
        String title,
        List<NoticeTeamResponse> teams,
        LocalDate createdAt
    ) {
        public static NoticeResponse from(Notice notice, List<NoticeTeam> noticeTeams) {
            return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .teams(noticeTeams.stream().map(NoticeTeamResponse::from).toList())
                .createdAt(notice.getCreatedAt())
                .build();
        }
    }
}
