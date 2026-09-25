package com.command.toyvillage_server.domain.app.auth.admin.presentation;

import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.request.EmployeeCreateRequest;
import com.command.toyvillage_server.domain.app.auth.admin.presentation.dto.response.EmployeeResponse;
import com.command.toyvillage_server.domain.app.auth.admin.service.EmployeeCreateService;
import com.command.toyvillage_server.domain.app.auth.admin.service.EmployeeDeleteService;
import com.command.toyvillage_server.domain.app.auth.admin.service.EmployeePasswordResetService;
import com.command.toyvillage_server.domain.app.auth.admin.service.QueryEmployeeListService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/admin")
@RequiredArgsConstructor
public class AppAdminController {
    private final EmployeeCreateService employeeCreateService;
    private final EmployeeDeleteService employeeDeleteService;
    private final QueryEmployeeListService queryEmployeeListService;
    private final EmployeePasswordResetService employeePasswordResetService;

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

    @DeleteMapping("/employees{appAdminId}")
    public ResponseEntity<MessageResponse> deleteEmployee(@PathVariable Long appAdminId) {
        employeeDeleteService.execute(appAdminId);
        return ResponseEntity.ok(MessageResponse.of("직원 계정이 삭제되었습니다."));

    }

    @PatchMapping("/employees/{appAdminId}/password")
    public ResponseEntity<MessageResponse> resetEmployeePassword(@PathVariable Long appAdminId) {
        employeePasswordResetService.execute(appAdminId);
        return ResponseEntity.ok(MessageResponse.of("직원 비밀번호가 아이디로 초기화되었습니다."));
    }
}
