package com.command.toyvillage_server.domain.app.dashboard.presentation;

import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardCountResponse;
import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardOverallOperationsResponse;
import com.command.toyvillage_server.domain.app.dashboard.presentation.dto.response.DashBoardQueryFeedLogResponse;
import com.command.toyvillage_server.domain.app.dashboard.service.DashBoardCountService;
import com.command.toyvillage_server.domain.app.dashboard.service.DashBoardOverallOperationsService;
import com.command.toyvillage_server.domain.app.dashboard.service.DashBoardQueryFeedLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {
    private final DashBoardCountService dashBoardCountService;
    private final DashBoardOverallOperationsService dashBoardOverallOperationsService;
    private final DashBoardQueryFeedLogService dashBoardQueryFeedLogService;

    @GetMapping("/count")
    public DashBoardCountResponse getCounts() {
        return dashBoardCountService.execute();
    }

    @GetMapping("/overall-operations")
    public DashBoardOverallOperationsResponse getOverallOperations() {
        return dashBoardOverallOperationsService.execute();
    }

    @GetMapping("/feed-logs")
    public Page<DashBoardQueryFeedLogResponse> getFeedLogs(
            @PageableDefault(size = 10, sort = {"feedDateTime", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return dashBoardQueryFeedLogService.execute(pageable);
    }

}
