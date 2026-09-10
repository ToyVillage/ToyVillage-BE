package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.auth.admin.facade.UserFacade;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.task.exception.TaskNotFoundException;
import com.command.toyvillage_server.domain.app.task.presentation.dto.response.TaskDetailResponse;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QueryTaskDetailService {
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
    public TaskDetailResponse execute(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> TaskNotFoundException.EXCEPTION);

        if (!userFacade.isCurrentUserAppAdmin() && !task.isAssignee(userFacade.getCurrentUserId())) {
            throw TaskNotFoundException.EXCEPTION;
        }

        return TaskDetailResponse.from(task, workReportRepository.findAllByTask_Id(id), LocalDate.now());
    }
}
