package com.command.toyvillage_server.domain.app.task.service;

import com.command.toyvillage_server.domain.app.auth.admin.domain.AppAdmin;
import com.command.toyvillage_server.domain.app.task.domain.Task;
import com.command.toyvillage_server.domain.app.task.domain.repository.TaskRepository;
import com.command.toyvillage_server.domain.app.task.presentation.dto.request.TaskRequest;
import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.service.FileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateTaskService {
    private final TaskRepository taskRepository;
    private final TaskAssigneeService taskAssigneeService;
    private final FileFacade fileFacade;

    @Transactional
    public Long execute(TaskRequest request) {
        List<AppAdmin> assignees = taskAssigneeService.execute(request.assigneeIds());
        List<File> files = fileFacade.findAllByKeys(request.files());

        Task task = Task.builder()
                .title(request.title())
                .content(request.content())
                .assignees(assignees)
                .finishDate(request.finishDate())
                .priority(request.priority())
                .files(files)
                .build();

        return taskRepository.save(task).getId();
    }
}
