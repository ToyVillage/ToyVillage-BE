package com.command.toyvillage_server.domain.web.faq.service;

import com.command.toyvillage_server.domain.web.faq.domain.repository.FaqRepository;
import com.command.toyvillage_server.domain.web.faq.presentation.dto.response.FaqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryFaqListService {
    private final FaqRepository faqRepository;

    @Transactional(readOnly = true)
    public List<FaqResponse> execute(Pageable p) {
        Pageable pageable = PageRequest.of(
            p.getPageNumber(),
            p.getPageSize(),
            Sort.by(Sort.Direction.DESC, "id")
        );

        return faqRepository.findAll(pageable)
            .map(FaqResponse::from)
            .toList();
    }
}
