package com.command.toyvillage_server.domain.web.news.service;

import com.command.toyvillage_server.domain.web.news.domain.News;
import com.command.toyvillage_server.domain.web.news.domain.repository.NewsRepository;
import com.command.toyvillage_server.domain.web.news.exception.NewsNotFoundException;
import com.command.toyvillage_server.domain.web.news.presentation.dto.response.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsQueryService {
    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public NewsResponse execute(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(()-> NewsNotFoundException.EXCEPTION);
        return NewsResponse.from(news);
    }
}
