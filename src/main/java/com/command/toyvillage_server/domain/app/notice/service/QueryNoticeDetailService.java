package com.command.toyvillage_server.domain.app.notice.service;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.notice.exception.NoticeNotFoundException;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.response.NoticeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QueryNoticeDetailService {
    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;

    @Transactional(readOnly = true)
    public NoticeDetailResponse execute(Long id) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        return NoticeDetailResponse.from(notice, noticeTeamRepository.findAllByNotice_IdOrderByIdAsc(id));
    }
}
