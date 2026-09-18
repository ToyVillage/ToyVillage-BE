package com.command.toyvillage_server.domain.app.notice.service;

import com.command.toyvillage_server.domain.app.notice.domain.Notice;
import com.command.toyvillage_server.domain.app.notice.domain.NoticeTeam;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeRepository;
import com.command.toyvillage_server.domain.app.notice.domain.repository.NoticeTeamRepository;
import com.command.toyvillage_server.domain.app.notice.exception.NoticeNotFoundException;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.request.NoticeRequestDto;
import com.command.toyvillage_server.domain.app.team.domain.Team;
import com.command.toyvillage_server.domain.app.team.domain.repository.TeamRepository;
import com.command.toyvillage_server.domain.app.team.exception.TeamNotFoundException;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeUpdateService {
    private final NoticeRepository noticeRepository;
    private final NoticeTeamRepository noticeTeamRepository;
    private final FileRepository fileRepository;
    private final TeamRepository teamRepository;


    @Transactional
    public void execute(Long id, NoticeRequestDto dto) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);

        List<Long> teamIds = dto.getTeamIds() == null
            ? List.of() : dto.getTeamIds().stream().distinct().toList();
        List<Team> teams = teamRepository.findAllById(teamIds);
        if (teams.size() != teamIds.size()) {
            throw TeamNotFoundException.EXCEPTION;
        }

        List<File> files = null;
        if (dto.getFiles() != null) {
            List<String> fileKeys = dto.getFiles();
            files = fileRepository.findAllByFileKeyIn(fileKeys);
            if (files.size() != fileKeys.size())
                throw FileNotFoundException.EXCEPTION;
        }

        List<NoticeTeam> noticeTeams = noticeTeamRepository.findAllByNotice_IdOrderByIdAsc(id);
        Set<Long> existingTeamIds = noticeTeams.stream()
            .map(noticeTeam -> noticeTeam.getTeam().getId())
            .collect(Collectors.toSet());
        noticeTeamRepository.deleteAll(noticeTeams.stream()
            .filter(noticeTeam -> !teamIds.contains(noticeTeam.getTeam().getId()))
            .toList());
        noticeTeamRepository.saveAll(teams.stream()
            .filter(team -> !existingTeamIds.contains(team.getId()))
            .map(team -> NoticeTeam.create(notice, team))
            .toList());

        notice.update(dto.getTitle(), dto.getContent(), files);
        noticeRepository.save(notice);
    }
}
