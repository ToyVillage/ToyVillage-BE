package com.command.toyvillage_server.domain.app.notice.presentation;

import com.command.toyvillage_server.domain.app.notice.presentation.dto.request.NoticeRequestDto;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.response.NoticeListResponseDto;
import com.command.toyvillage_server.domain.app.notice.presentation.dto.response.NoticeDetailResponse;
import com.command.toyvillage_server.domain.app.notice.service.NoticeCreateService;
import com.command.toyvillage_server.domain.app.notice.service.NoticeDeleteService;
import com.command.toyvillage_server.domain.app.notice.service.NoticeUpdateService;
import com.command.toyvillage_server.domain.app.notice.service.QueryNoticeDetailService;
import com.command.toyvillage_server.domain.app.notice.service.QueryNoticeListService;
import com.command.toyvillage_server.global.common.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final QueryNoticeDetailService queryNoticeDetailService;
    private final QueryNoticeListService queryNoticeListService;
    private final NoticeUpdateService noticeUpdateService;
    private final NoticeDeleteService noticeDeleteService;
    private final NoticeCreateService noticeCreateService;

    @PutMapping("/{notice-id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse update(@Valid @RequestBody NoticeRequestDto request, @PathVariable("notice-id") Long noticeId) {
        noticeUpdateService.execute(noticeId, request);
        return MessageResponse.of("공지 수정 성공");
    }

    @GetMapping("/{notice-id}")
    @ResponseStatus(HttpStatus.OK)
    public NoticeDetailResponse getDetail(@PathVariable("notice-id") Long noticeId) {
        return queryNoticeDetailService.execute(noticeId);
    }

    @GetMapping
    public NoticeListResponseDto getList(
        @RequestParam(required = false) Long teamId,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return queryNoticeListService.execute(teamId, pageable);
    }


    @DeleteMapping("/{notice-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<MessageResponse> deleteNotice(@PathVariable("notice-id") Long noticeId) {
        noticeDeleteService.execute(noticeId);
        return ResponseEntity.ok(
                new MessageResponse("공지 삭제가 완료되었습니다.")
        );
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createNotice(@RequestBody @Valid NoticeRequestDto request) {
        noticeCreateService.execute(request);

        return ResponseEntity.ok(
            new MessageResponse("공지사항이 생성되었습니다.")
        );
    }
}
