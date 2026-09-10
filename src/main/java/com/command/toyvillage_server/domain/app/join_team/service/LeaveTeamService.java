package com.command.toyvillage_server.domain.app.join_team.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.join_team.domain.repository.JoinTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveTeamService {
    private final JoinTeamRepository joinTeamRepository;
    private final AppAdminRepository appAdminRepository;

    @Transactional
    public void execute(Long appAdminId) {
        if (!appAdminRepository.existsById(appAdminId)) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        joinTeamRepository.findByAppAdmin_Id(appAdminId)
                .ifPresent(joinTeamRepository::delete);
    }
}
