package com.command.toyvillage_server.domain.app.notice.service;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.response.NoticeListResponseDto;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QueryNoticeListService {
    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public NoticeListResponseDto execute(Long teamId, Pageable p) {
        if (teamId != null && !teamRepository.existsById(teamId)) {
            throw TeamNotFoundException.EXCEPTION;
        }

        Pageable pageable = PageRequest.of(
            p.getPageNumber(),
            p.getPageSize(),
            p.getSortOr(Sort.by(Sort.Direction.DESC, "id"))
        );

        Page<Notice> notices = teamId == null
            ? noticeRepository.findAll(pageable)
            : noticeRepository.findAllByTeamId(teamId, pageable);

        Map<Long, List<NoticeTeam>> noticeTeams = noticeTeamRepository
            .findAllByNotice_IdInOrderByIdAsc(notices.map(Notice::getId).toList()).stream()
            .collect(Collectors.groupingBy(noticeTeam -> noticeTeam.getNotice().getId()));

        return NoticeListResponseDto.from(notices, noticeTeams);
    }
}
