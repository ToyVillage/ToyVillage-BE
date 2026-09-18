package com.command.toyvillage_server.domain.app.notice.service;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.request.NoticeRequestDto;
import com.command.toyvillage_server.domain.app.team.domain.Team;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeCreateService {
    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;
    private final FileRepository fileRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void execute(NoticeRequestDto request) {
        List<Long> teamIds = request.getTeamIds() == null
            ? List.of() : request.getTeamIds().stream().distinct().toList();
        List<Team> teams = teamRepository.findAllById(teamIds);
        if (teams.size() != teamIds.size()) {
            throw TeamNotFoundException.EXCEPTION;
        }

        List<String> fileKeys = request.getFiles() == null ? new ArrayList<>() : request.getFiles();
        List<File> files = fileRepository.findAllByFileKeyIn(fileKeys);
        if (files.size() != fileKeys.size())
            throw FileNotFoundException.EXCEPTION;


        Notice notice = Notice.create(
            request.getTitle(),
            request.getKind(),
            request.getContent(),
            files
        );

        noticeRepository.save(notice);
        noticeTeamRepository.saveAll(teams.stream()
            .map(team -> NoticeTeam.create(notice, team))
            .toList());
    }
}
