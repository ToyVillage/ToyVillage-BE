package com.command.toyvillage_server.domain.web.news.presentation;

import com.command.toyvillage_server.domain.web.news.presentation.dto.request.NewsRequest;
import com.command.toyvillage_server.domain.web.news.presentation.dto.response.MessageResponse;
import com.command.toyvillage_server.domain.web.news.presentation.dto.response.NewsResponse;
import com.command.toyvillage_server.domain.web.news.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsCreateService newsCreateService;
    private final NewsQueryAllService newsQueryAllService;
    private final NewsQueryService newsQueryService;
    private final NewsDeleteService newsDeleteService;
    private final NewsUpdateService newsUpdateService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody NewsRequest newsRequest) {
        newsCreateService.execute(newsRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("뉴스가 추가되었습니다."));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<NewsResponse> getQueryAllNews(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return newsQueryAllService.execute(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsResponse getQueryNews(@PathVariable Long id) {
        return newsQueryService.execute(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> deleteQueryNews(@PathVariable Long id) {
        newsDeleteService.execute(id);
        return ResponseEntity.ok(new MessageResponse("뉴스가 삭제되었습니다."));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> updateQueryNews(@PathVariable Long id,@Valid @RequestBody NewsRequest newsRequest) {
        newsUpdateService.execute(id, newsRequest);
        return ResponseEntity.ok(new MessageResponse("뉴스가 수정되었습니다."));
    }
}
