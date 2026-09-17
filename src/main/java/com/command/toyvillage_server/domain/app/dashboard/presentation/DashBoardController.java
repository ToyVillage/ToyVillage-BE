package com.command.toyvillage_server.domain.app.dashboard.presentation;

import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardCountResponse;
import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardOverallOperationsResponse;
import com.command.toyvillage_server.domain.app.dashboard.service.DashBoardCountService;
import com.command.toyvillage_server.domain.app.dashboard.service.DashBoardOverallOperationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {
    private final DashBoardCountService dashBoardCountService;
    private final DashBoardOverallOperationsService dashBoardOverallOperationsService;

    @GetMapping("/count")
    public DashBoardCountResponse getCounts() {
        return dashBoardCountService.execute();
    }

    @GetMapping("/overall-operations")
    public DashBoardOverallOperationsResponse getOverallOperations() {
        return dashBoardOverallOperationsService.execute();
    }
}
