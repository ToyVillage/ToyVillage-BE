package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.task.exception.TaskNotFoundException;
import com.command.toyvillage_server.domain.app.workreport.domain.repository.WorkReportRepository;
import com.command.toyvillage_server.domain.app.workreport.exception.WorkReportAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteTaskService {
    private final TaskRepository taskRepository;
    private final WorkReportRepository workReportRepository;

    @Transactional
    public void execute(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> TaskNotFoundException.EXCEPTION);

        if (!workReportRepository.findAllByTask_Id(id).isEmpty()) {
            throw WorkReportAlreadyExistsException.EXCEPTION;
        }

        taskRepository.delete(task);
    }
}
