package com.command.toyvillage_server.domain.app.notice.service;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.notice.exception.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeDeleteService {

    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;

    @Transactional
    public void execute(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);
        noticeTeamRepository.deleteAllByNotice_Id(noticeId);
        noticeTeamRepository.flush();
        noticeRepository.delete(notice);
    }
}
