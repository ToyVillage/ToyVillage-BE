package com.command.toyvillage_server.domain.web.news.service;

import com.command.toyvillage_server.domain.web.file.domain.File;
import com.command.toyvillage_server.domain.web.file.domain.repository.FileRepository;
import com.command.toyvillage_server.domain.web.file.exception.FileNotFoundException;
import com.command.toyvillage_server.domain.web.news.domain.News;
import com.command.toyvillage_server.domain.web.news.domain.repository.NewsRepository;
import com.command.toyvillage_server.domain.web.news.exception.NewsNotFoundException;
import com.command.toyvillage_server.domain.web.news.presentation.dto.request.NewsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsUpdateService {
    private final NewsRepository newsRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void execute(Long id, NewsRequest newsRequest) {
        News news = newsRepository.findById(id)
                .orElseThrow(()-> NewsNotFoundException.EXCEPTION);
        List<File> files = news.getFiles();
        if (newsRequest.getFileKeys() != null){
            if(newsRequest.getFileKeys().isEmpty()){
                files = new ArrayList<>();
            }else {
                files = fileRepository.findAllByFileKeyIn(newsRequest.getFileKeys());
                if(files.size() != newsRequest.getFileKeys().size()){
                    throw FileNotFoundException.EXCEPTION;
                }
            }
        }

        news.update(
                newsRequest.getTitle(),
                newsRequest.getDescription(),
                files
        );
        log.info("뉴스 수정 /id : {}",news.getId());
    }
}
