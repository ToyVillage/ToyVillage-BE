package com.command.toyvillage_server.domain.app.close_day.presentation;

import com.command.toyvillage_server.domain.app.close_day.presentation.dto.request.CloseDayRequest;
import com.command.toyvillage_server.domain.app.close_day.presentation.dto.response.CloseDayResponse;
import com.command.toyvillage_server.domain.app.close_day.service.CloseDayCreateService;
import com.command.toyvillage_server.domain.app.close_day.service.CloseDayDeleteService;
import com.command.toyvillage_server.domain.app.close_day.service.CloseDayUpdateService;
import com.command.toyvillage_server.domain.app.close_day.service.QueryCloseDayByDateService;
import com.command.toyvillage_server.domain.app.close_day.service.QueryCloseDayListService;
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
@RequestMapping("/close-day")
public class CloseDayController {
    private final CloseDayCreateService closeDayCreateService;
    private final CloseDayUpdateService closeDayUpdateService;
    private final CloseDayDeleteService closeDayDeleteService;
    private final QueryCloseDayListService queryCloseDayListService;
    private final QueryCloseDayByDateService queryCloseDayByDateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponse> createCloseDay(@RequestBody @Valid CloseDayRequest request) {
        closeDayCreateService.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageResponse.of("휴관일이 생성되었습니다."));
    }

    @PutMapping("/{close-day-id}")
    public ResponseEntity<MessageResponse> updateCloseDay(
            @PathVariable("close-day-id") Long closeDayId,
            @RequestBody @Valid CloseDayRequest request
    ) {
        closeDayUpdateService.execute(closeDayId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.of("휴관일이 수정되었습니다."));
    }

    @DeleteMapping("/{close-day-id}")
    public ResponseEntity<MessageResponse> deleteCloseDay(@PathVariable("close-day-id") Long closeDayId) {
        closeDayDeleteService.execute(closeDayId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageResponse.of("휴관일이 삭제되었습니다."));
    }

    @GetMapping
    public List<CloseDayResponse> getCloseDays() {
        return queryCloseDayListService.execute();
    }

    @GetMapping("/date")
    public List<CloseDayResponse> getCloseDaysByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return queryCloseDayByDateService.execute(date);
    }
}
