package com.command.toyvillage_server.domain.app.auth.admin.presentation;

import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.EmployeeCreateRequest;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response.EmployeeResponse;
import com.command.toyvillage_server.domain.app.auth.admin.service.EmployeeCreateService;
import com.command.toyvillage_server.domain.app.auth.admin.service.QueryEmployeeListService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/app/admin")
@RequiredArgsConstructor
public class AppAdminController {
    private final EmployeeCreateService employeeCreateService;
    private final QueryEmployeeListService queryEmployeeListService;

    @PostMapping("/employees")
    public ResponseEntity<MessageResponse> createEmployee(
            @RequestBody @Valid EmployeeCreateRequest request
    ) {
        employeeCreateService.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("직원이 생성되었습니다."));
    }

    @GetMapping("/employees")
    public List<EmployeeResponse> getEmployees() {
        return queryEmployeeListService.execute();
    }
}
