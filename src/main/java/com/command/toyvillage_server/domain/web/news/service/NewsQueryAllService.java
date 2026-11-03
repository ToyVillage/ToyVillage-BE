package com.command.toyvillage_server.domain.web.news.service;

import com.command.toyvillage_server.domain.web.news.domain.repository.NewsRepository;
import com.command.toyvillage_server.domain.web.news.presentation.dto.response.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsQueryAllService {
    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public Page<NewsResponse> execute(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(NewsResponse::from);
    }
}
