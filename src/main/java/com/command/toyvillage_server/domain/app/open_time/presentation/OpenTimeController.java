package com.command.toyvillage_server.domain.app.open_time.presentation;

import com.command.toyvillage_server.domain.app.open_time.presentation.dto.request.OpenTimeRequest;
import com.command.toyvillage_server.domain.app.open_time.presentation.dto.response.OpenTimeResponse;
import com.command.toyvillage_server.domain.app.open_time.service.OpenTimeCreateService;
import com.command.toyvillage_server.domain.app.open_time.service.OpenTimeDeleteService;
import com.command.toyvillage_server.domain.app.open_time.service.OpenTimeUpdateService;
import com.command.toyvillage_server.domain.app.open_time.service.QueryOpenTimeByDateService;
import com.command.toyvillage_server.domain.app.open_time.service.QueryOpenTimeDetailService;
import com.command.toyvillage_server.domain.app.open_time.service.QueryOpenTimeListService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-time")
public class OpenTimeController {
    private final OpenTimeCreateService openTimeCreateService;
    private final OpenTimeUpdateService openTimeUpdateService;
    private final OpenTimeDeleteService openTimeDeleteService;
    private final QueryOpenTimeListService queryOpenTimeListService;
    private final QueryOpenTimeByDateService queryOpenTimeByDateService;
    private final QueryOpenTimeDetailService queryOpenTimeDetailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponse> createOpenTime(@RequestBody @Valid OpenTimeRequest request) {
        openTimeCreateService.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("운영시간이 생성되었습니다."));
    }

    @PutMapping("/{open-time-id}")
    public ResponseEntity<MessageResponse> updateOpenTime(
            @PathVariable("open-time-id") Long openTimeId,
            @RequestBody @Valid OpenTimeRequest request
    ) {
        openTimeUpdateService.execute(openTimeId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.of("운영시간이 수정되었습니다."));
    }

    @DeleteMapping("/{open-time-id}")
    public ResponseEntity<MessageResponse> deleteOpenTime(@PathVariable("open-time-id") Long openTimeId) {
        openTimeDeleteService.execute(openTimeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.of("운영시간이 삭제되었습니다."));
    }

    @GetMapping
    public List<OpenTimeResponse> getOpenTimes() {
        return queryOpenTimeListService.execute();
    }

    @GetMapping("/date")
    public List<OpenTimeResponse> getOpenTimesByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return queryOpenTimeByDateService.execute(date);
    }

    @GetMapping("/{open-time-id}")
    public OpenTimeResponse getOpenTime(@PathVariable("open-time-id") Long openTimeId) {
        return queryOpenTimeDetailService.execute(openTimeId);
    }
}
