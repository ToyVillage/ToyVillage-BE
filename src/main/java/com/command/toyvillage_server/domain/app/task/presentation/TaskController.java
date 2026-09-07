package com.command.toyvillage_server.domain.app.task.presentation;

import com.command.toyvillage_server.domain.app.task.domain.TaskStatus;
import com.command.toyvillage_server.domain.app.task.presentation.dto.request.TaskRequest;
import com.command.toyvillage_server.domain.app.task.presentation.dto.response.TaskDetailResponse;
import com.command.toyvillage_server.domain.app.task.presentation.dto.response.TaskListResponse;
import com.command.toyvillage_server.domain.app.task.service.CreateTaskService;
import com.command.toyvillage_server.domain.app.task.service.DeleteTaskService;
import com.command.toyvillage_server.domain.app.task.service.QueryMyTaskListService;
import com.command.toyvillage_server.domain.app.task.service.QueryTaskDetailService;
import com.command.toyvillage_server.domain.app.task.service.QueryTaskListService;
import com.command.toyvillage_server.domain.app.task.service.UpdateTaskService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final CreateTaskService createTaskService;
    private final QueryTaskListService queryTaskListService;
    private final QueryMyTaskListService queryMyTaskListService;
    private final QueryTaskDetailService queryTaskDetailService;
    private final UpdateTaskService updateTaskService;
    private final DeleteTaskService deleteTaskService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody TaskRequest request) {
        Long id = createTaskService.execute(request);
        return ResponseEntity.created(URI.create("/tasks/" + id))
                .body(MessageResponse.of("업무지시가 등록되었습니다."));
    }

    @GetMapping
    public TaskListResponse getList(
            @RequestParam(required = false) TaskStatus status,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return queryTaskListService.execute(status, pageable);
    }

    @GetMapping("/my")
    public TaskListResponse getMyList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return queryMyTaskListService.execute(pageable);
    }

    @GetMapping("/{id}")
    public TaskDetailResponse getDetail(@PathVariable Long id) {
        return queryTaskDetailService.execute(id);
    }

    @PutMapping("/{id}")
    public MessageResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        updateTaskService.execute(id, request);
        return MessageResponse.of("업무지시가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable Long id) {
        deleteTaskService.execute(id);
        return MessageResponse.of("업무지시가 삭제되었습니다.");
    }
}
