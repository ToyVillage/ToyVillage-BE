package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.auth.admin.domain.repository.AppAdminRepository;
import com.command.toyvillage_server.domain.app.auth.admin.exception.AppAdminNotFoundException;
import com.command.toyvillage_server.domain.app.task.exception.TaskTargetInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskAssigneeService {
    private final AppAdminRepository appAdminRepository;

    public List<AppAdmin> execute(List<Long> assigneeIds) {
        if (assigneeIds == null || assigneeIds.isEmpty()) {
            throw TaskTargetInvalidException.EXCEPTION;
        }

        List<Long> distinctIds = assigneeIds.stream().distinct().toList();
        List<AppAdmin> assignees = appAdminRepository.findAllById(distinctIds);

        if (assignees.size() != distinctIds.size()) {
            throw AppAdminNotFoundException.EXCEPTION;
        }

        if (assignees.stream().anyMatch(AppAdmin::isAppAdmin)) {
            throw TaskTargetInvalidException.EXCEPTION;
        }

        return assignees;
    }
}
