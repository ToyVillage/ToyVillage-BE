package com.command.toyvillage_server.domain.web.faq.service;

import com.command.toyvillage_server.domain.web.faq.domain.Faq;
import com.command.toyvillage_server.domain.web.faq.domain.repository.FaqRepository;
import com.command.toyvillage_server.domain.web.faq.presentation.dto.request.FaqRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateFaqService {
    private final FaqRepository faqRepository;

    @Transactional
    public Long execute(FaqRequest request) {
        Faq faq = Faq.builder()
            .content(request.getQuestionContent())
            .answer(request.getQuestionAnswer())
            .build();
        faqRepository.save(faq);
        log.info("자주 묻는 질문 생성 / Id : {}", faq.getId());
        return faq.getId();
    }
}
