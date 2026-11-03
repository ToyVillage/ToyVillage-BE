package com.command.toyvillage_server.domain.web.news.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.news.domain.News;
import com.command.toyvillage_server.domain.web.news.domain.repository.NewsRepository;
import com.command.toyvillage_server.domain.web.news.presentation.dto.request.NewsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCreateService {
    private final NewsRepository newsRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(NewsRequest newsRequest) {
        List<File> files = new ArrayList<>();
        if (newsRequest.getFileKeys() != null && !newsRequest.getFileKeys().isEmpty()) {
            files = fileRepository.findAllByFileKeyIn(newsRequest.getFileKeys());
            if (files.size() != newsRequest.getFileKeys().size()) {
                throw FileNotFoundException.EXCEPTION;
            }
        }

        News news = News.create(
                newsRequest.getTitle(),
                newsRequest.getDescription(),
                files
        );
        newsRepository.save(news);
        log.info("뉴스 생성됨: {}", news.getId());
    }
}
